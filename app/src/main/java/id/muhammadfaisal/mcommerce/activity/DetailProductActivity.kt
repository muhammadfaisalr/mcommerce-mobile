package id.muhammadfaisal.mcommerce.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.databinding.ActivityDetailProductBinding
import id.muhammadfaisal.mcommerce.util.BottomSheets
import id.muhammadfaisal.mcommerce.util.Constant
import id.muhammadfaisal.mcommerce.util.Formatter
import id.muhammadfaisal.mcommerce.viewmodel.OrderViewModel

@AndroidEntryPoint
class DetailProductActivity : AppCompatActivity() {

    private val TAG = "DetailProductActivity"

    private lateinit var binding : ActivityDetailProductBinding
    private var inventoryResponse: InventoryResponse? = null

    private val viewModel by viewModels<OrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityDetailProductBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)
        this.extras()
    }

    @SuppressLint("NewApi")
    private fun extras() {
        val extras = this.intent.extras
        this.inventoryResponse = extras?.getParcelable(Constant.Key.INVENTORY)
        this.inventoryResponse?.let {
            Log.d(TAG, "extras: $it")
            this.binding.product = it
            this.binding.textPrice.text = Formatter.formatRupiah(it.price)
            Glide.with(this).load(it.image).into(this.binding.imageProduct)
        }

        this.binding.buttonPurchase.setOnClickListener {
            this.inventoryResponse?.let {
                BottomSheets.placeOrder(this, it, 1)
            }
        }

        this.binding.buttonCart.setOnClickListener {
            this.inventoryResponse?.let {
                BottomSheets.placeOrder(this, it, 0)
            }
        }
    }

    companion object {

    }
}