package com.sayaradz.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.TwoVersions
import com.sayaradz.models.Version
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


class TwoVersionsViewModel(var id1: String, var id2: String) : ViewModel() {

    private lateinit var twoVersionsObserver: Observer<TwoVersions>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val versionLiveData: MutableLiveData<TwoVersions> = MutableLiveData()

    init {
        getData(this.id1, this.id2)
    }

    fun getData(id1: String, id2: String) {
        twoVersionsObserver = getTwoVersionsObserver()
        val version1Observable = ApiService.invoke().getVersion(id1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        val version2Observable = ApiService.invoke().getVersion(id2)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        val combined =
            Observable.zip(
                version1Observable,
                version2Observable,
                BiFunction<Version, Version, TwoVersions> { version1, version2 ->
                    TwoVersions(version1, version2)
                })

        combined.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(twoVersionsObserver)
    }

    private fun getTwoVersionsObserver(): Observer<TwoVersions> {
        return object : Observer<TwoVersions> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: TwoVersions) {
                versionLiveData.value = s
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