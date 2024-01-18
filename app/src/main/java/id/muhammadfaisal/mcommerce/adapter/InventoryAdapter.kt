package id.muhammadfaisal.mcommerce.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.activity.DetailProductActivity
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.databinding.ItemInventoryBinding
import id.muhammadfaisal.mcommerce.helper.GeneralHelper
import id.muhammadfaisal.mcommerce.util.BottomSheets
import id.muhammadfaisal.mcommerce.util.Constant
import id.muhammadfaisal.mcommerce.util.Formatter

class InventoryAdapter constructor(
    private val inventories: List<InventoryResponse>
) : RecyclerView.Adapter<InventoryAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = ItemInventoryBinding.bind(view)

        fun bind(inventoryResponse: InventoryResponse) {
            binding.textTitle.text = inventoryResponse.name
            binding.textPrice.text = Formatter.formatRupiah(inventoryResponse.price)
            Glide.with(itemView.context).load(inventoryResponse.image).into(binding.imageProduct)

            binding.imageMore.setOnClickListener {
                BottomSheets.showMenu(it.context as AppCompatActivity, inventoryResponse)
            }

            binding.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putParcelable(Constant.Key.INVENTORY, inventoryResponse)
                GeneralHelper.move(it.context, DetailProductActivity::class.java, false, bundle = bundle)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false))
    }

    override fun getItemCount(): Int {
        return inventories.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(inventories[position])
    }
}