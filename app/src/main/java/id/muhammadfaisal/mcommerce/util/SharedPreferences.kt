package id.muhammadfaisal.mcommerce.util

import android.content.Context
import android.content.SharedPreferences
import android.provider.ContactsContract.Data
import android.util.Log
import com.google.gson.Gson
import id.muhammadfaisal.mcommerce.api.request.OrderItem
import id.muhammadfaisal.mcommerce.model.Cart


class SharedPreferences {

    companion object {
        var sharedPreferences: SharedPreferences? = null
        var invoices: ArrayList<String>? = null
        var carts: ArrayList<Cart>? = null

        fun getInstance(context: Context): SharedPreferences {
            if (sharedPreferences == null) {
                sharedPreferences = context.getSharedPreferences("mcommerce", Context.MODE_PRIVATE)
                invoices = ArrayList()
                carts = ArrayList()
            }
            return sharedPreferences!!
        }

        fun addToCart(context: Context, product: Cart) {
            Log.d("SharedPreference", "Saved Product $product")
            val editor = getInstance(context).edit()

            carts?.add(product)
            val gson = Gson()
            val json = gson.toJson(carts)

            editor.putString(Constant.Key.CART, json)
            editor.apply()
        }

        fun getCart(context: Context): ArrayList<Cart>? {
            val gson = Gson()
            val json = getInstance(context).getString(Constant.Key.CART, null)
            val type = object : com.google.gson.reflect.TypeToken<ArrayList<Cart>>() {}.type
            carts = gson.fromJson(json, type)
            return carts
        }

        fun clearCart(context: Context) {
            val editor = getInstance(context).edit()

            carts?.clear()
            val gson = Gson()
            val json = gson.toJson(carts)

            editor.putString(Constant.Key.CART, json)
            editor.apply()
        }

        fun saveInvoice(context: Context, invoice: String) {
            Log.d("SharedPreference", "Saved Invoice $invoice")
            val editor = getInstance(context).edit()

            invoices?.add(invoice)
            val gson = Gson()
            val json = gson.toJson(invoices)

            editor.putString(Constant.Key.INVOICE, json)
            editor.apply()
        }

        fun getInvoice(context: Context): ArrayList<String>? {
            val gson = Gson()
            val json = getInstance(context).getString(Constant.Key.INVOICE, null)
            val type = object : com.google.gson.reflect.TypeToken<ArrayList<String>>() {}.type
            invoices = gson.fromJson(json, type)
            return invoices
        }


    }
}