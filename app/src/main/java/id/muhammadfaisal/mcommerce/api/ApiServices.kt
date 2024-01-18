package id.muhammadfaisal.mcommerce.api

import id.muhammadfaisal.mcommerce.api.request.AddProductRequest
import id.muhammadfaisal.mcommerce.api.request.PlaceOrderRequest
import id.muhammadfaisal.mcommerce.api.response.InventoryResponse
import id.muhammadfaisal.mcommerce.api.response.InvoiceDetailResponse
import id.muhammadfaisal.mcommerce.api.response.PlaceOrderResponse
import id.muhammadfaisal.mcommerce.util.Constant
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiServices {
    @GET(Constant.BACKEND_URL + Constant.PATH.INVENTORY)
    fun getInventories(): Observable<List<InventoryResponse>>

    @POST(Constant.BACKEND_URL + Constant.PATH.INVENTORY)
    fun addInventory(@Body addProductRequest: AddProductRequest): Observable<InventoryResponse>

    @POST(Constant.BACKEND_URL + Constant.PATH.ORDER)
    fun placeOrder(@Body placeOrderRequest: PlaceOrderRequest) : Observable<PlaceOrderResponse>

    @GET(Constant.BACKEND_URL + Constant.PATH.ORDER + "/{invoice}")
    fun getDetailOrder(@Path("invoice") invoice: String) : Observable<InvoiceDetailResponse>

}