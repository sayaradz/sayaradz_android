package com.sayaradz.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Version
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AvailableVersionsViewModel(var id: String) : ViewModel() {

    private lateinit var availableVersionsObserver: Observer<List<Version>>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val availableVersionsLiveData: MutableLiveData<List<Version>> = MutableLiveData()

    init {
        getData()
    }

    private fun getData() {
        availableVersionsObserver = getAvailableVersionsObserver()
        ApiService.invoke().getAvailableVersions(this.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(availableVersionsObserver)
    }

    private fun getAvailableVersionsObserver(): Observer<List<Version>> {
        return object : Observer<List<Version>> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: List<Version>) {
                availableVersionsLiveData.value = s
            }

            override fun onError(e: Throwable) {
                Log.e("Connection Error", e.message)
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
