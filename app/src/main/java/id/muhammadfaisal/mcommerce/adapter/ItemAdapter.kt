package id.muhammadfaisal.mcommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponseDetail
import id.muhammadfaisal.mcommerce.databinding.ItemPurchasedBinding
import id.muhammadfaisal.mcommerce.util.Formatter

class ItemAdapter(private val orderItems: List<PlaceOrderResponseDetail>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private var binding = ItemPurchasedBinding.bind(view)

        fun bind(orderItem: PlaceOrderResponseDetail) {
            binding.textProductName.text = orderItem.name
            binding.textQuantity.text = "${orderItem.quantity} x ${Formatter.formatRupiah(orderItem.price.toInt())}"
            binding.textTotalAmount.text = Formatter.formatRupiah((orderItem.quantity * orderItem.price).toInt())
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