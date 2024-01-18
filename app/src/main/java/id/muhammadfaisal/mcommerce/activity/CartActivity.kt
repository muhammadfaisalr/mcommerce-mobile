package id.muhammadfaisal.mcommerce.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.mcommerce.DaggerAppComponent
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.adapter.CartAdapter
import id.muhammadfaisal.mcommerce.api.repository.PlaceOrderRepository
import id.muhammadfaisal.mcommerce.databinding.ActivityCartBinding
import id.muhammadfaisal.mcommerce.helper.GeneralHelper
import id.muhammadfaisal.mcommerce.util.Constant
import id.muhammadfaisal.mcommerce.util.Formatter
import id.muhammadfaisal.mcommerce.util.SharedPreferences
import id.muhammadfaisal.mcommerce.viewmodel.OrderViewModel
import id.muhammadfaisal.mcommerce.viewmodel.factory.PlaceOrderViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var viewModel: OrderViewModel

    @Inject
    lateinit var placeOrderRepository: PlaceOrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityCartBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        DaggerAppComponent.create().inject(this)
        this.setup()
    }

    private fun setup() {
        this.viewModel = ViewModelProvider(this, PlaceOrderViewModelFactory(placeOrderRepository))[OrderViewModel::class.java]
        val carts = SharedPreferences.getCart(this) ?: listOf()
        this.binding.recyclerView.adapter = CartAdapter(carts)
        this.binding.recyclerView.layoutManager = LinearLayoutManager(this)
        this.binding.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        //get total amount from carts
        var totalAmount = 0
        carts.forEach {
            totalAmount += it.amount.toInt() * it.quantity
        }

        this.binding.textTotalAmount.text = Formatter.formatRupiah(totalAmount )

        this.binding.imageBack.setOnClickListener {
            finish()
        }

        this.binding.buttonPurchase.setOnClickListener {
            this.viewModel.purchaseFromCart(carts)
        }

        this.viewModel.error.observe(this) {
            it?.let {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }

        this.viewModel.loading.observe(this) {
            if (it) {
                this.binding.buttonPurchase.isEnabled = false
                this.binding.buttonPurchase.text = "Loading..."
                this.binding.progressBar.show()
            } else {
                this.binding.buttonPurchase.isEnabled = true
                this.binding.buttonPurchase.text = "Purchase"
                this.binding.progressBar.hide()
            }
        }

        this.binding.buttonPurchase.setOnClickListener {
            this.viewModel.purchaseFromCart(carts)
        }

        this.viewModel.placeOrderResponse.observe(this) {
            it?.let {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                if (it.status == "SUCCESS") {
                    val bundle = Bundle()
                    bundle.putParcelable(Constant.Key.ORDER_SUCCESS, it)
                    GeneralHelper.move(this, OrderSuccessActivity::class.java, true, bundle)
                    SharedPreferences.clearCart(this)
                    setResult(Activity.RESULT_OK)
                }
            }
        }
    }
}