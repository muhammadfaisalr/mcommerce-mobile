package id.muhammadfaisal.mcommerce.api.repository

import id.muhammadfaisal.mcommerce.api.ApiServices
import id.muhammadfaisal.mcommerce.api.request.AddProductRequest
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.util.RetrofitBuilder
import io.reactivex.Observable

class InventoryRepository(private val provideApiService: ApiServices) {
    fun getInventories(): Observable<List<InventoryResponse>> {
        return provideApiService.getInventories()
    }

    fun addInventory(addProductRequest: AddProductRequest): Observable<InventoryResponse> {
        return provideApiService.addInventory(addProductRequest)
    }
}