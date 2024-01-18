package id.muhammadfaisal.mcommerce.util

import id.muhammadfaisal.mcommerce.BuildConfig

class Constant {
    companion object {
        const val BACKEND_URL = BuildConfig.BACKEND_URL
    }

    class PATH {
        companion object {
            const val INVENTORY = "inventory"
            const val ORDER = "orders"
            const val ADD_STOCK = "/add-stock"
        }
    }

    class Key {
        companion object {
            const val CART = "cart"
            const val INVENTORY = "inventory"
            const val ORDER_SUCCESS = "order_success"
            const val INVOICE = "invoice"
        }
    }
}