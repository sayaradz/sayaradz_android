package com.sayaradz.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Followed
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FollowModelViewModel : ViewModel() {

    private lateinit var followedObserver: Observer<Followed>
    val fol: MutableLiveData<Boolean> = MutableLiveData()


    fun getData(id: String, followed: String) {
        followedObserver = getBrandsObserver()
        ApiService.invoke().followModel(id, Followed(followed))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(followedObserver)
    }

    private fun getBrandsObserver(): Observer<Followed> {
        return object : Observer<Followed> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: Followed) {
                fol.value = true
            }

            override fun onError(e: Throwable) {
                fol.value = false
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
