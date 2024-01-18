package id.muhammadfaisal.mcommerce.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.adapter.ItemAdapter
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponse
import id.muhammadfaisal.mcommerce.databinding.ActivityOrderSuccessBinding
import id.muhammadfaisal.mcommerce.helper.GeneralHelper
import id.muhammadfaisal.mcommerce.util.Constant
import id.muhammadfaisal.mcommerce.util.Formatter
import id.muhammadfaisal.mcommerce.util.SharedPreferences
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

class OrderSuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderSuccessBinding

    private val TAG = OrderSuccessActivity::class.simpleName
    private var placeOrderResponse: PlaceOrderResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityOrderSuccessBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        this.extras()
        this.bind()
        this.move()
    }

    private fun bind() {
        this.binding.itemReceipt.recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        this.binding.itemReceipt.recyclerView.adapter = ItemAdapter(this.placeOrderResponse?.orderItems ?: listOf())
        this.binding.itemReceipt.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        //calculate total quantity in placeOrderResponse?.orderItems
        val totalAmount = this.placeOrderResponse?.orderItems?.sumOf { it.price * it.quantity } ?: 0
        this.binding.itemReceipt.textTotal.text = Formatter.formatRupiah((totalAmount).toInt())
        this.binding.itemReceipt.textInvoiceDate.text = this.placeOrderResponse?.createdAt
    }

    private fun move() {
        CompositeDisposable().add(
            Observable.timer(5, TimeUnit.SECONDS)
                .subscribe {
                    GeneralHelper.move(this, HomeActivity::class.java)
                }
        )
    }

    private fun extras() {
        val extras = this.intent.extras
        extras?.let {
            placeOrderResponse = it.getParcelable(Constant.Key.ORDER_SUCCESS)
        }

        this.placeOrderResponse?.let {
            SharedPreferences.saveInvoice(this, it.invoiceNumber)
            this.binding.itemReceipt.textInvoiceNumber.text = it.invoiceNumber
        }
    }
}