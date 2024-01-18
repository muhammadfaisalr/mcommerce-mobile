package id.muhammadfaisal.mcommerce.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.muhammadfaisal.mcommerce.DaggerAppComponent
import id.muhammadfaisal.mcommerce.activity.OrderSuccessActivity
import id.muhammadfaisal.mcommerce.api.repository.PlaceOrderRepository
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.databinding.FragmentPlaceOrderBottomSheetDialogBinding
import id.muhammadfaisal.mcommerce.helper.GeneralHelper
import id.muhammadfaisal.mcommerce.util.Constant
import id.muhammadfaisal.mcommerce.util.SharedPreferences
import id.muhammadfaisal.mcommerce.viewmodel.OrderViewModel
import id.muhammadfaisal.mcommerce.viewmodel.factory.PlaceOrderViewModelFactory
import javax.inject.Inject

const val ARG_PARAM_STATE = "ARG_PARAM_STATE"
class PlaceOrderBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentPlaceOrderBottomSheetDialogBinding
    private var inventoryResponse: InventoryResponse? = null

    private var state: Int? = null

    @Inject
    lateinit var placeOrderRepository: PlaceOrderRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            inventoryResponse = it.getParcelable(ARG_PARAM_OBJECT)
            state = it.getInt(ARG_PARAM_STATE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        this.binding = FragmentPlaceOrderBottomSheetDialogBinding.inflate(this.layoutInflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        DaggerAppComponent.create().inject(this)
        val viewModel by lazy {
            ViewModelProvider(
                viewModelStore,
                PlaceOrderViewModelFactory(placeOrderRepository)
            )[OrderViewModel::class.java]
        }
        this.binding.inventory = this.inventoryResponse

        //State 1 is Purchasing
        if (this.state == 1) {
            this.binding.buttonPurchase.visibility = View.VISIBLE
            this.binding.buttonCart.visibility = View.GONE
        } else {
            //State 0 is Add to Cart
            this.binding.buttonPurchase.visibility = View.GONE
            this.binding.buttonCart.visibility = View.VISIBLE
        }

        this.binding.viewModel = viewModel
        viewModel.loading.observe(requireActivity()) {
            if (it) {
                this.isCancelable = false
                this.binding.inputQuantity.isEnabled = false
                this.binding.buttonPurchase.isEnabled = false
                this.binding.progressIndicator.show()
            } else {
                this.isCancelable = true
                this.binding.inputQuantity.isEnabled = true
                this.binding.buttonPurchase.isEnabled = true
                this.binding.progressIndicator.hide()
            }
        }

        viewModel.error.observe(requireActivity()) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.placeOrderResponse.observe(requireActivity()) {
            if (it.status == "SUCCESS") {
                val bundle = Bundle()
                bundle.putParcelable(Constant.Key.ORDER_SUCCESS, it)
                GeneralHelper.move(requireContext(), OrderSuccessActivity::class.java, true, bundle)
            }
            dismiss()
            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
        }

        viewModel.cart.observe(requireActivity()) {
            SharedPreferences.addToCart(requireContext(), it)
            Toast.makeText(requireContext(), "Added to Cart!", Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: InventoryResponse, state: Int) = PlaceOrderBottomSheetDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_PARAM_OBJECT, param1)
                putInt(ARG_PARAM_STATE, state)
            }
        }
    }
}