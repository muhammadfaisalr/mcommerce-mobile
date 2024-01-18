package id.muhammadfaisal.mcommerce.bottomsheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.adapter.ItemAdapter
import id.muhammadfaisal.mcommerce.adapter.ItemInvoiceAdapter
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailResponse
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponse
import id.muhammadfaisal.mcommerce.databinding.FragmentReceiptBottomSheetDialogBinding
import id.muhammadfaisal.mcommerce.util.Constant
import id.muhammadfaisal.mcommerce.util.Formatter

private const val ARG_PARAM1 = Constant.Key.INVOICE

class ReceiptBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var param1: InvoiceDetailResponse? = null
    private lateinit var binding: FragmentReceiptBottomSheetDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentReceiptBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        param1?.let {
            val invoiceItem = this.binding.layoutInvoice
            invoiceItem.textInvoiceNumber.text = it.invoiceNumber
            invoiceItem.textInvoiceDate.text = it.createdAt
            invoiceItem.textTotal.text = Formatter.formatRupiah(it.total.toInt())


            invoiceItem.recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            invoiceItem.recyclerView.adapter = ItemInvoiceAdapter(it.orderItems)
            invoiceItem.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: InvoiceDetailResponse) =
            ReceiptBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                }
            }
    }
}