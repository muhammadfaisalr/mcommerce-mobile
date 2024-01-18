package id.muhammadfaisal.mcommerce.api.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import lombok.Data

data class PlaceOrderRequest(
    private val orderDetail: List<OrderItem>
)

@Data
@Parcelize
data class OrderItem(
    val name: String,
    val productId: Long,
    val quantity: Int,
    val amount: Long
) : Parcelable