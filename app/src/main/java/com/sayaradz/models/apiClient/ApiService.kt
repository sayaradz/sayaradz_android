package com.sayaradz.models.apiClient

import com.sayaradz.models.*
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
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

    @GET("commands/brands/{id}/models/availables")
    fun getAvailableModels(
        @Path("id") id: String
    ): Observable<List<Model>>

    @GET("commands/models/{id}/versions/availables")
    fun getAvailableVersions(
        @Path("id") id: String
    ): Observable<List<Version>>

    @GET("commands/versions/{id}/options/availables")
    fun getAvailableOptions(
        @Path("id") id: String
    ): Observable<List<Option>>

    @GET("commands/versions/{id}/colors/availables")
    fun getAvailableColors(
        @Path("id") id: String
    ): Observable<List<Color>>

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("users/{userId}/follows/versions")
    fun followVersion(
        @Path("userId") userId: String,
        @Body followed: Followed
    ): Observable<Followed>

    @DELETE("users/{userId}/follows/versions/{versionId}")
    fun unfollowVersion(
        @Path("userId") userId: String,
        @Path("versionId") versionId: String
    ): Observable<String>

    @GET("orders/trending/versions")
    fun getTrendingVersions(): Observable<List<Version>>

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
