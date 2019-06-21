package com.sayaradz.models.apiClient

import com.sayaradz.models.Brand
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.Model
import com.sayaradz.models.Version
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET("brands")
    fun getBrands(): Observable<BrandsResponse>

    @GET("brands/{id}")
    fun getBrand(
        @Path("id") id: String
    ): Observable<Brand>


    @GET("models/{id}")
    fun getModel(
        @Path("id") id: String
    ): Observable<Model>

    @GET("versions/{id}")
    fun getVersion(
        @Path("id") id: String
    ): Observable<Version>


    companion object {
        operator fun invoke(): ApiService {

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .baseUrl("https://sayaradz-backend.herokuapp.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
