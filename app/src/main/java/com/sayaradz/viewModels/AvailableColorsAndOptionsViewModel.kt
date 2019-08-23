package com.sayaradz.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.*
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers


class AvailableColorsAndOptionsViewModel(var id: String) : ViewModel() {

    private lateinit var optionsAndColorsObserver: Observer<OptionsAndColors>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val optionsAndColorsLiveData: MutableLiveData<OptionsAndColors> = MutableLiveData()

    init {
        getData()
    }

    private fun getData() {
        optionsAndColorsObserver = getOptionsAndColorsObserver()
        val colorsObservable = ApiService.invoke().getAvailableColors(this.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        val optionsObservable = ApiService.invoke().getAvailableOptions(this.id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())

        val combined =
            Observable.zip(
                colorsObservable,
                optionsObservable,
                BiFunction<List<Color>,List<Option>, OptionsAndColors> { colors, options ->
                    OptionsAndColors(options , colors)
                })

        combined.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(optionsAndColorsObserver)
    }

    private fun getOptionsAndColorsObserver(): Observer<OptionsAndColors> {
        return object : Observer<OptionsAndColors> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: OptionsAndColors) {
                optionsAndColorsLiveData.value = s
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