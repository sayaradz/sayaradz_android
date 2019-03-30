package com.sayaradz.viewModels

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable


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