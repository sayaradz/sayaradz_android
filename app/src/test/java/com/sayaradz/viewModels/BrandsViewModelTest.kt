package com.sayaradz.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.Gson
import com.jraska.livedata.test
import com.sayaradz.models.BrandsResponse
import com.sayaradz.models.apiClient.ApiService
import com.sayaradz.views.activities.BrandsActivity
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


@RunWith(JUnit4::class)
class BrandsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var userService: ApiService

    lateinit var mainViewModel: BrandsViewModel
    lateinit var brands: BrandsResponse

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val json =
            "{\"rows\":[{\"models\":[],\"_id\":\"5d177692f222b900176c7282\",\"code\":\"peugeo\",\"name\":\"Peugeot\",\"logo\":\"https://upload.wikimedia.org/wikipedia/fr/thumb/b/b9/Peugeot_logo2009.png/280px-Peugeot_logo2009.png\",\"createdAt\":\"2019-06-29T14:32:50.229Z\",\"updatedAt\":\"2019-06-29T14:32:50.229Z\",\"__v\":0},{\"models\":[],\"_id\":\"5d177681f222b900176c7281\",\"code\":\"peug\",\"name\":\"Peugeot\",\"logo\":\"https://upload.wikimedia.org/wikipedia/fr/thumb/b/b9/Peugeot_logo2009.png/280px-Peugeot_logo2009.png\",\"createdAt\":\"2019-06-29T14:32:33.173Z\",\"updatedAt\":\"2019-06-29T14:32:33.173Z\",\"__v\":0},{\"models\":[{\"image_url\":\"\",\"_id\":\"5d0406617f58fa00173774cb\",\"code\":\"508\",\"name\":\"508\",\"createdAt\":\"2019-06-14T20:41:05.505Z\",\"updatedAt\":\"2019-06-29T16:08:55.938Z\",\"__v\":0},{\"image_url\":\"https://img.autoplus.fr/news/2019/05/07/1538310/1350%7C900%7C4b109d0f0ec8cc465523cca6.jpg\",\"_id\":\"5d1ca3eaccaf46001764339d\",\"code\":\"203 pegout\",\"name\":\"203\",\"createdAt\":\"2019-07-03T12:47:38.829Z\",\"updatedAt\":\"2019-07-03T15:26:54.433Z\",\"__v\":0}],\"_id\":\"5d177603f222b900176c727f\",\"code\":\"mercedes\",\"name\":\"Mercedes\",\"logo\":\"https://images-na.ssl-images-amazon.com/images/I/61VaoHj7IbL._SX425_.jpg\",\"createdAt\":\"2019-06-29T14:30:27.304Z\",\"updatedAt\":\"2019-07-03T12:47:58.792Z\",\"__v\":0},{\"models\":[{\"image_url\":\"\",\"_id\":\"5d177c20f222b900176c7284\",\"code\":\"cooode\",\"name\":\"skiko\",\"createdAt\":\"2019-06-29T14:56:32.495Z\",\"updatedAt\":\"2019-07-12T17:49:05.672Z\",\"__v\":0},{\"image_url\":\"\",\"_id\":\"5d28dafa52104f0017a393cb\",\"code\":\"newcode\",\"name\":\"newOne\",\"createdAt\":\"2019-07-12T19:09:46.977Z\",\"updatedAt\":\"2019-07-12T19:10:03.269Z\",\"__v\":0}],\"_id\":\"5d0e64dd6c5d750017f46454\",\"code\":\"volkswagen\",\"name\":\"Volkswagen\",\"logo\":\"https://img.autoplus.fr/news/2011/07/31/1444138/1350%7C900%7C317df5b8a3e8138ba67ab013.jpg?r\",\"createdAt\":\"2019-06-22T17:26:53.907Z\",\"updatedAt\":\"2019-07-12T19:09:47.493Z\",\"__v\":0},{\"models\":[],\"_id\":\"5c816b8908333d00178a0e03\",\"code\":\"peugeot\",\"name\":\"Peugeot\",\"logo\":\"https://upload.wikimedia.org/wikipedia/fr/thumb/b/b9/Peugeot_logo2009.png/280px-Peugeot_logo2009.png\",\"createdAt\":\"2019-03-07T19:05:45.451Z\",\"updatedAt\":\"2019-06-22T18:33:02.109Z\",\"__v\":0}],\"count\":5}"
        val gson = Gson()
        brands = gson.fromJson(json, BrandsResponse::class.java)
    }

    @Test
    fun getBrandLiveData() {

        this.mainViewModel = ViewModelProviders.of(this).get(BrandsViewModel::class.java)
        this.mainViewModel.brandLiveData.test()
            .awaitValue()
            .assertHasValue()
            .assertValue(brands)
    }
}