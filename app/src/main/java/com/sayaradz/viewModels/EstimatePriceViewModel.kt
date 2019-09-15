package com.sayaradz.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Tarif
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class EstimatePriceViewModel : ViewModel() {

    private lateinit var tarifObserver: Observer<Tarif>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val Price: MutableLiveData<Tarif> = MutableLiveData()


    fun getData(colorId: String, OptionsId: String, versionId: String) {
        tarifObserver = getTarifObserver()
        ApiService.invoke().estimatePrice(colorId, OptionsId, versionId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(tarifObserver)
    }

    private fun getTarifObserver(): Observer<Tarif> {
        return object : Observer<Tarif> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: Tarif) {
                Price.value = s
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
