package id.muhammadfaisal.mcommerce.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.api.request.OrderItem
import id.muhammadfaisal.mcommerce.databinding.ItemCartBinding
import id.muhammadfaisal.mcommerce.model.Cart
import id.muhammadfaisal.mcommerce.util.Formatter


class CartAdapter(
    private val carts: List<Cart>
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private var binding = ItemCartBinding.bind(view)
        fun bind(orderItem: Cart) {
            this.binding.textName.text = orderItem.name
            this.binding.textQty.text = "${orderItem.quantity}x${Formatter.formatRupiah(orderItem.amount.toInt())}"
            this.binding.textPrice.text = Formatter.formatRupiah((orderItem.amount * orderItem.quantity).toInt())
            Glide.with(itemView.context).load(orderItem.image).into(this.binding.imageProduct)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false))
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        holder.bind(carts[position])
    }

    override fun getItemCount(): Int {
        return this.carts.size
    }
}