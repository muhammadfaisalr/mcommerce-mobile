package id.muhammadfaisal.mcommerce.util

import androidx.appcompat.app.AppCompatActivity
import id.muhammadfaisal.mcommerce.activity.DetailProductActivity
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailResponse
import id.muhammadfaisal.mcommerce.bottomsheet.ActionBottomSheetDialogFragment
import id.muhammadfaisal.mcommerce.bottomsheet.PlaceOrderBottomSheetDialogFragment
import id.muhammadfaisal.mcommerce.bottomsheet.ReceiptBottomSheetDialogFragment

class BottomSheets {
    companion object {
        const val TAG = "BottomSheets"

        fun showMenu(appCompatActivity: AppCompatActivity, inventoryResponse: InventoryResponse) {
            val bottomSheetFragment = ActionBottomSheetDialogFragment.newInstance(inventoryResponse)
            bottomSheetFragment.show(appCompatActivity.supportFragmentManager, bottomSheetFragment.tag)
        }

        fun placeOrder(detailProductActivity: DetailProductActivity, inventoryResponse: InventoryResponse, state: Int) {
            val bottomSheetFragment = PlaceOrderBottomSheetDialogFragment.newInstance(inventoryResponse, state)
            bottomSheetFragment.show(detailProductActivity.supportFragmentManager, bottomSheetFragment.tag)
        }

        fun showReceipt(appCompatActivity: AppCompatActivity, invoice: InvoiceDetailResponse) {
            val bottomSheetFragment = ReceiptBottomSheetDialogFragment.newInstance(invoice)
            bottomSheetFragment.show(appCompatActivity.supportFragmentManager, bottomSheetFragment.tag)
        }
    }
}