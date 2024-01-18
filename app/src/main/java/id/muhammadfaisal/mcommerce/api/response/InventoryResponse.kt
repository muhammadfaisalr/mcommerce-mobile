package id.muhammadfaisal.mcommerce.api.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class InventoryResponse(
    val id: Int,
    val name: String,
    val price: Int,
    val stock: Int,
    val description: String,
    val image: String,
    override var createdAt: String?,
    override var updatedAt: String?,
    override var deletedAt: String?,
) : BaseResponse(), Parcelable

