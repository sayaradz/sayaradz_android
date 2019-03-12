package com.sayaradz.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Brand
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class NewCarsViewModel : ViewModel() {

    private var topBrands: List<Brand> = ArrayList()
    private var myCompositeDisposable: CompositeDisposable? = null


    fun getTopBrands(): List<Brand>{

        loadData()
        return topBrands
    }

    private fun loadData() {

//Define the Retrofit request//

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val requestInterface = Retrofit.Builder()
            .baseUrl("https://sayaradz-backend.herokuapp.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(ApiService::class.java)

        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(requestInterface.getBrands(10)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse))
    }

    private fun handleResponse(brandResponse: BrandsResponse) {

        topBrands = ArrayList(brandResponse.brands)
        Log.e("Fail", topBrands.size.toString())
    }



}
