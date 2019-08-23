package com.sayaradz.viewModels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sayaradz.models.User
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UserViewModel(var email: String, var user_id: String, var fcmToken: String) : ViewModel() {

    private lateinit var followedObserver: Observer<User>
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val contentViewVisibility: MutableLiveData<Int> = MutableLiveData()
    val internetErrorVisibility: MutableLiveData<Int> = MutableLiveData()

    val brandLiveData: MutableLiveData<User> = MutableLiveData()


    init {
        getData()
    }


    fun getData() {
        followedObserver = getBrandsObserver()
        ApiService.invoke().updateUser(User(email, user_id, fcmToken))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(followedObserver)
    }

    private fun getBrandsObserver(): Observer<User> {
        return object : Observer<User> {
            override fun onSubscribe(d: Disposable) {
                //Log.d(TAG, "onSubscribe")
            }

            override fun onNext(s: User) {
                brandLiveData.value = s
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
