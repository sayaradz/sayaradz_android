package com.elbadri.apps.sayaradz.activity.Models


import io.reactivex.Observable
import retrofit2.http.*

interface ApiClient {

    @GET("brands")
    fun getBrands() : Observable<BrandsJson>





}