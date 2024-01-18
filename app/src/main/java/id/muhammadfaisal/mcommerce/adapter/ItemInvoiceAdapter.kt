package id.muhammadfaisal.mcommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailItemResponse
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailResponse
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponseDetail
import id.muhammadfaisal.mcommerce.databinding.ItemPurchasedBinding
import id.muhammadfaisal.mcommerce.util.Formatter

class ItemInvoiceAdapter(private val orderItems: List<InvoiceDetailItemResponse>) : RecyclerView.Adapter<ItemInvoiceAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var binding = ItemPurchasedBinding.bind(view)

        fun bind(orderItem: InvoiceDetailItemResponse) {
            binding.textProductName.text = orderItem.name
            binding.textQuantity.text = "${orderItem.quantity} x ${Formatter.formatRupiah(orderItem.amount.toInt())}"
            binding.textTotalAmount.text = Formatter.formatRupiah((orderItem.totalAmount).toInt())
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_purchased, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderItems[position])
    }

    override fun getItemCount(): Int {
        return orderItems.size
    }
}