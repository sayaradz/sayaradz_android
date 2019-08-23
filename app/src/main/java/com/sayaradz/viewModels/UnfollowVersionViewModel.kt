package com.sayaradz.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.Followed
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UnfollowVersionViewModel(var id: String, var versionId: String) : ViewModel() {

    private lateinit var followedObserver: Observer<String>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val brandLiveData: MutableLiveData<BrandsResponse> = MutableLiveData()


    init {
        getData()
    }


    fun getData() {
        followedObserver = getBrandsObserver()
        ApiService.invoke().unfollowVersion(id,versionId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(followedObserver)
    }

    private fun getBrandsObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: String) {

            }

            override fun onError(e: Throwable) {
                loadingVisibility.value = View.GONE
                internetErrorVisibility.value = View.VISIBLE
            }

            override fun onComplete() {
                loadingVisibility.value = View.GONE
                contentViewVisibility.value = View.VISIBLE
            }
        }

    }

    companion object {
        operator fun invoke() {

        }
    }


}
