package id.muhammadfaisal.mcommerce.helper

import android.content.Context
import android.content.Intent
import android.os.Bundle

class GeneralHelper {
    companion object {
        fun move(
            context: Context,
            destination: Class<*>,
            forget: Boolean = false,
            bundle: Bundle? = null
        ) {
            context.startActivity(Intent(context, destination).apply {
                if (forget) {
                    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                bundle?.let {
                    this.putExtras(it)
                }
            })
        }
    }
}