package id.muhammadfaisal.mcommerce.api.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PlaceOrderResponse(
    val message: String,
    val status: String,
    val invoiceNumber: String,
    val orderItems: List<PlaceOrderResponseDetail>,
    override var createdAt: String?,
    override var updatedAt: String?,
    override var deletedAt: String?
) : Parcelable, BaseResponse()

@Parcelize
data class PlaceOrderResponseDetail(
    val name: String,
    val quantity: Int,
    val price: Long,
) : Parcelable