package id.muhammadfaisal.mcommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.databinding.ItemInvoicesBinding
import id.muhammadfaisal.mcommerce.viewmodel.OrderViewModel

class InvoiceAdapter(
    private val invoiceNumbers: List<String>,
    private val viewModel: OrderViewModel
) : RecyclerView.Adapter<InvoiceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var binding = ItemInvoicesBinding.bind(view)

        fun bind(s: String, viewModel: OrderViewModel) {
            binding.text.text = s
            binding.root.setOnClickListener {
                viewModel.invoice.value = s
                viewModel.findInvoice();
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_invoices, parent, false))
    }

    override fun getItemCount(): Int {
        return invoiceNumbers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(invoiceNumbers[position], viewModel)
    }
}