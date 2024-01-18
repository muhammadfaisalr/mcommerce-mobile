package id.muhammadfaisal.mcommerce.api.repository

import id.muhammadfaisal.mcommerce.api.ApiServices
import id.muhammadfaisal.mcommerce.api.request.PlaceOrderRequest
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailResponse
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponse
import io.reactivex.Observable

class PlaceOrderRepository(private val provideApiService: ApiServices) {

    fun placeOrder(placeOrderRequest: PlaceOrderRequest): Observable<PlaceOrderResponse> {
        return provideApiService.placeOrder(placeOrderRequest)
    }

    fun getDetailOrder(invoice: String): Observable<InvoiceDetailResponse> {
        return provideApiService.getDetailOrder(invoice)
    }
}
