package id.muhammadfaisal.mcommerce.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.mcommerce.R
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.databinding.FragmentActionBottomSheetDialogBinding

const val ARG_PARAM_OBJECT = "ARG_PARAM_OBJECT"

class ActionBottomSheetDialogFragment : BottomSheetDialogFragment() {
    private var inventoryResponse: InventoryResponse? = null

    private lateinit var binding: FragmentActionBottomSheetDialogBinding

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            inventoryResponse = it.getParcelable(ARG_PARAM_OBJECT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentActionBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: InventoryResponse) =
            ActionBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM_OBJECT, param1)
                }
            }
    }
}