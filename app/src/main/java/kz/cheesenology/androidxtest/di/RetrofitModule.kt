package kz.cheesenology.androidxtest.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.FieldNamingStrategy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import kz.cheesenology.androidxtest.network.NetworkApi
import kz.cheesenology.androidxtest.network.NetworkInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.reflect.Field
import javax.inject.Singleton

@Module
class RetrofitModule {
    @Singleton
    @Provides
    fun provideInterceptor(): NetworkInterceptor { // This is where the Interceptor object is constructed
        return NetworkInterceptor.get()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: NetworkInterceptor): OkHttpClient { // The Interceptor is then added to the client
        val httpInterceptor = HttpLoggingInterceptor()
        httpInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(httpInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(
        gson: Gson,
        okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory
    ): Retrofit.Builder { // The Client is then added to Retrofit
        return Retrofit.Builder()
            .baseUrl("http://www.google.com")
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(converterFactory)
    }

    @Singleton
    @Provides
    fun provideNetworkApi(builder: Retrofit.Builder): NetworkApi {
        return builder.build().create<NetworkApi>(NetworkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .setLenient()
            .setFieldNamingStrategy(CustomFieldNamingPolicy())
            .setPrettyPrinting()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .serializeNulls()
            .create()
    }

    private class CustomFieldNamingPolicy : FieldNamingStrategy {
        override fun translateName(field: Field): String {
            var name = FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES.translateName(field)
            name = name.substring(2, name.length).toLowerCase()
            return name
        }
    }
}