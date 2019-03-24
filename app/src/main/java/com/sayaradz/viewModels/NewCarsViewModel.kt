package com.sayaradz.viewModels

import androidx.lifecycle.ViewModel
import com.sayaradz.models.Brand
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class NewCarsViewModel : ViewModel() {

    //private var topBrands: Observable<List<Brand>>
    private var myCompositeDisposable: CompositeDisposable? = null


    /*fun loadData(): CompositeDisposable {

        myCompositeDisposable = CompositeDisposable()
        myCompositeDisposable?.add(
            ApiService.invoke().getBrands(10)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
        return myCompositeDisposable as CompositeDisposable
    }*/

    /*private fun handleResponse(brandResponse: BrandsResponse) {

        topBrands = brandResponse.brands

    }*/


}