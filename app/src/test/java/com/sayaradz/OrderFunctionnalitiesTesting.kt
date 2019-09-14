package com.sayaradz

import com.sayaradz.models.*
import com.sayaradz.models.apiClient.ApiService
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import java.text.SimpleDateFormat
import java.util.*


@RunWith(JUnit4::class)
class OrderFunctionnalitiesTesting {


    @Mock
    lateinit var userService: ApiService
    lateinit var order: Order
    //TODO initialize with the right data
    var versionId = "5d04090a7f58fa00173774d5"
    var modelId = "5d6593421f23cc0017cb7281"
    var brandId = "5d6517829e3627001704cdc3"
    var orderVersionId = "5d04090a7f58fa00173774d5"
    var userId = "uVQ5q9gzPFQ8UJyIgU1aCkgt9nY2"

    @Before
    fun setUp() {

        userService = ApiService.invoke()

        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        order = Order(orderVersionId, null, null, formatter.format(date), null, null, null, userId)
    }

    @Test
    fun getBrands() {

        this.userService.getBrands().subscribe(object : Observer<BrandsResponse> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: BrandsResponse) {
                Assert.assertTrue(t.brands.size == t.count && t.count != 0)
            }

            override fun onError(e: Throwable) {

            }

        })

    }

    @Test
    fun getBrand() {

        this.userService.getBrand(brandId).subscribe(object : Observer<Brand> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Brand) {
                Assert.assertTrue(t.id === brandId)
            }

            override fun onError(e: Throwable) {

            }

        })

    }

    @Test
    fun getModel() {
        this.userService.getModel(modelId).subscribe(object : Observer<Model> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Model) {
                Assert.assertTrue(t.id == modelId)
            }

            override fun onError(e: Throwable) {

            }

        })
    }

    @Test
    fun getVersion() {
        this.userService.getVersion(versionId).subscribe(object : Observer<Version> {
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {

            }

            override fun onNext(t: Version) {
                Assert.assertTrue(t.id == versionId)
            }

            override fun onError(e: Throwable) {

            }

        })
    }

    @Test
    fun createOrder() {
        this.userService.createOrder(order)
            .subscribe(object : Observer<Order> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Order) {
                    Assert.assertNotNull(t)
                }

                override fun onError(e: Throwable) {

                }

            })
    }
}