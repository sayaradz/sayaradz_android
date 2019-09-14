package com.sayaradz.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Order
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CreateOrderViewModel() : ViewModel() {

    private lateinit var followedObserver: Observer<Order>
    val state: MutableLiveData<Boolean> = MutableLiveData()

    lateinit var disposable: Disposable

    val brandLiveData: MutableLiveData<Order> = MutableLiveData()


    fun getData(torder: Order) {
        Log.d("testing API", torder.toString())
        followedObserver = getBrandsObserver()
        ApiService.invoke().createOrder(torder)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(followedObserver)
    }

    private fun getBrandsObserver(): Observer<Order> {
        return object : Observer<Order> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(s: Order) {
                brandLiveData.value = s
                state.value = true
            }

            override fun onError(e: Throwable) {
                state.value = false
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
