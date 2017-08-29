package com.arthurnagy.droidconberlin

import com.arthurnagy.droidconberlin.model.Session
import io.reactivex.Single
import retrofit2.http.GET


interface DroidconApiService {

    // start & end queries are always the same, so it's fine to hardcode them
    @GET("eventfeed/0?start=2017-09-04&end=2017-09-08")
    fun getSchedule(): Single<List<Session>>

}