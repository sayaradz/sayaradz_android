package com.sayaradz.viewModels

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Model
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class AvailableModelsViewModel(var id: String) : ViewModel() {

    private lateinit var availableModelObserver: Observer<List<Model>>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val availableModelsLiveData: MutableLiveData<List<Model>> = MutableLiveData()

    init {
        getData()
    }

    private fun getData() {
        availableModelObserver = getAvailableModelsObserver()
        ApiService.invoke().getAvailableModels(this.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(availableModelObserver)
    }

    private fun getAvailableModelsObserver(): Observer<List<Model>> {
        return object : Observer<List<Model>> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: List<Model>) {
                availableModelsLiveData.value = s
            }

            override fun onError(e: Throwable) {
                loadingVisibility.value = View.GONE
                internetErrorVisibility.value = View.VISIBLE
                Log.e("Conection error", e.message)
            }

            override fun onComplete() {
                loadingVisibility.value = View.GONE
                contentViewVisibility.value = View.VISIBLE
            }
        }

    }


}
