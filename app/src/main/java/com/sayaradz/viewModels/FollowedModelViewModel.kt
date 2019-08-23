package com.sayaradz.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.FollowedModelsResponse
import com.sayaradz.models.Model
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FollowedModelViewModel(var id: String) : ViewModel() {

    private lateinit var versionObserver: Observer<FollowedModelsResponse>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val versionLiveData: MutableLiveData<FollowedModelsResponse> = MutableLiveData()

    init {
        getData()
    }

    private fun getData() {
        versionObserver = getVersionObserver()
        ApiService.invoke().getFollowedModels(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(versionObserver)
    }

    private fun getVersionObserver(): Observer<FollowedModelsResponse> {
        return object : Observer<FollowedModelsResponse> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: FollowedModelsResponse) {
                versionLiveData.value = s
                Log.d("kjhkj",s.count.toString() + "-" + id)
            }

            override fun onError(e: Throwable) {
                loadingVisibility.value = View.GONE
                internetErrorVisibility.value = View.VISIBLE
                Log.e("kjhkj", e.message)
            }

            override fun onComplete() {
                loadingVisibility.value = View.GONE
                contentViewVisibility.value = View.VISIBLE
            }
        }

    }


}
