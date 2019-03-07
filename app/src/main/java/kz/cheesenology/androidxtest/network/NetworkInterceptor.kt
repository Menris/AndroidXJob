package kz.cheesenology.androidxtest.network

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class NetworkInterceptor private constructor() : Interceptor {


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val newUrl = original.url().newBuilder()
            .scheme("http")
            .host("terminal.cheesenology.kz")
            .build()

        original = original.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(original)
    }

    companion object {
        private var sInterceptor: NetworkInterceptor? = null

        @Inject
        fun get(): NetworkInterceptor {
            if (sInterceptor == null) {
                sInterceptor = NetworkInterceptor()
            }
            return sInterceptor as NetworkInterceptor
        }
    }
}
