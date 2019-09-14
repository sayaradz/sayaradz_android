package com.sayaradz.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UnfollowModelViewModel : ViewModel() {

    private lateinit var followedObserver: Observer<String>
    val unfol: MutableLiveData<Boolean> = MutableLiveData()

    val brandLiveData: MutableLiveData<BrandsResponse> = MutableLiveData()


    fun getData(id: String, modelId: String) {
        followedObserver = getBrandsObserver()
        ApiService.invoke().unfollowModel(id, modelId)
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
                unfol.value = true
            }

            override fun onError(e: Throwable) {
                unfol.value = false
            }

            override fun onComplete() {

            }
        }

    }

    companion object {
        operator fun invoke() {

        }
    }


}
