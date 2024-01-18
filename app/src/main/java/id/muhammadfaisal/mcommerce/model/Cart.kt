package id.muhammadfaisal.mcommerce.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import lombok.Data

@Data
@Parcelize
data class Cart(
    val name: String,
    val productId: Long,
    val quantity: Int,
    val amount: Long,
    val image: String
) : Parcelable