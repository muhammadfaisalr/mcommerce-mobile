package id.muhammadfaisal.mcommerce.api.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InvoiceDetailResponse (
    var id: Long,
    var invoiceNumber: String,
    var total: Long,
    var orderItems: List<InvoiceDetailItemResponse>,
    override var createdAt: String?,
    override var updatedAt: String?,
    override var deletedAt: String?
) : Parcelable, BaseResponse()

@Parcelize
data class InvoiceDetailItemResponse(
    var id: Long,
    var orderId: Long,
    var productId: Long,
    var quantity: Long,
    var amount: Long,
    var name: String,
    var totalAmount: Long,
    override var createdAt: String?,
    override var updatedAt: String?,
    override var deletedAt: String?
): Parcelable, BaseResponse()