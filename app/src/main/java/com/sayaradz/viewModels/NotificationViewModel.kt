package com.sayaradz.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.Brand
import com.sayaradz.models.Notification
import com.sayaradz.models.NotifsResponse
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NotificationViewModel(var id: String) : ViewModel() {

    private lateinit var brandObserver: Observer<NotifsResponse>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val modelLiveData: MutableLiveData<List<Notification>> = MutableLiveData()

    init {
        getData(this.id)
    }

    private fun getData(id: String) {
        brandObserver = getBrandObserver()
        ApiService.invoke().getNotificationList(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(brandObserver)
    }

    private fun getBrandObserver(): Observer<NotifsResponse> {
        return object : Observer<NotifsResponse> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: NotifsResponse) {
                modelLiveData.value = s.notifs
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
