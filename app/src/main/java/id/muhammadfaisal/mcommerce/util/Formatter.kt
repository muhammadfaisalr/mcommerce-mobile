package id.muhammadfaisal.mcommerce.util

import java.text.NumberFormat
import java.util.Locale

class Formatter {
    companion object {
        fun formatRupiah(price: Int): String {
            val number = NumberFormat.getNumberInstance(Locale("ID", "ID")).format(price)
            return "Rp$number"
        }
    }
}