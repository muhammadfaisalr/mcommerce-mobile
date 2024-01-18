package id.muhammadfaisal.mcommerce.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import id.muhammadfaisal.mcommerce.adapter.InvoiceAdapter
import id.muhammadfaisal.mcommerce.databinding.ActivityHistoryBinding
import id.muhammadfaisal.mcommerce.util.BottomSheets
import id.muhammadfaisal.mcommerce.util.SharedPreferences
import id.muhammadfaisal.mcommerce.viewmodel.OrderViewModel

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private val viewModel by viewModels<OrderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityHistoryBinding.inflate(this.layoutInflater)
        this.setContentView(this.binding.root)

        this.binding.viewModel = viewModel

        this.binding.imageBack.setOnClickListener {
            finish()
        }

        this.viewModel.loading.observe(this) {
            if (it) {
                this.binding.progressIndicator.show()
            } else {
                this.binding.progressIndicator.hide()
            }
        }

        this.viewModel.invoiceDetailResponse.observe(this) {
            if (it != null) {
                BottomSheets.showReceipt(this, it)
            }
        }

        this.binding.recyclerView.adapter = InvoiceAdapter(SharedPreferences.getInvoice(this) ?: listOf(), this.viewModel)
        this.binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        this.binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }
}