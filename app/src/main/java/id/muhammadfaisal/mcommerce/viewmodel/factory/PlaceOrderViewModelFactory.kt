package id.muhammadfaisal.mcommerce.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import id.muhammadfaisal.mcommerce.api.repository.PlaceOrderRepository
import id.muhammadfaisal.mcommerce.viewmodel.OrderViewModel
import javax.inject.Inject

class PlaceOrderViewModelFactory @Inject constructor(private val placeOrderRepository: PlaceOrderRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(placeOrderRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}