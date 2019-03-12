package com.sayaradz.models.apiClient

import androidx.lifecycle.LiveData
import com.sayaradz.models.BrandsResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("brands")
    fun getBrands(
        @Query("limit") limit: Int
    ): Observable<BrandsResponse>

    companion object {
        operator fun invoke(
//            connectivityInterceptor: ConnectivityInterceptor
        ): ApiService {
//            val requestInterceptor = Interceptor { chain ->
//
//                val url = chain.request()
//                    .url()
//                    .newBuilder()
//                    .addQueryParameter("key", API_KEY)
//                    .build()
//
//                val request = chain.request()
//                    .newBuilder()
//                    .url(url)
//                    .build()
//
//                return@Interceptor chain.proceed(request)
//            }

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://sayaradz-backend.herokuapp.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
