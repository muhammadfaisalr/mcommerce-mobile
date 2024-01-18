package id.muhammadfaisal.mcommerce.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor

class LoggerInterceptor : Interceptor {
    private val logger = HttpLoggingInterceptor.Logger { message ->
        println(message)
    }

    private val httpLoggingInterceptor = HttpLoggingInterceptor(logger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return httpLoggingInterceptor.intercept(chain)
    }
}