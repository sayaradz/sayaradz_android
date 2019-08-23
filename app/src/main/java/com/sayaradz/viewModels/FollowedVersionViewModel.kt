package com.sayaradz.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.FollowedVersionsResponse
import com.sayaradz.models.Version
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FollowedVersionViewModel(var id: String) : ViewModel() {

    private lateinit var versionObserver: Observer<FollowedVersionsResponse>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val versionLiveData: MutableLiveData<FollowedVersionsResponse> = MutableLiveData()

    init {
        getData(this.id)
    }

    private fun getData(id: String) {
        versionObserver = getVersionObserver()
        ApiService.invoke().getFollowedVersions(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(versionObserver)
    }

    private fun getVersionObserver(): Observer<FollowedVersionsResponse> {
        return object : Observer<FollowedVersionsResponse> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: FollowedVersionsResponse) {
                versionLiveData.value = s
                Log.d("kjhkj",s.count.toString() + "-" + s.versions)
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


}
