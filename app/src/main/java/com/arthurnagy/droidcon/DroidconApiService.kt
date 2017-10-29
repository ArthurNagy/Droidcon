package com.arthurnagy.droidcon

import com.arthurnagy.droidcon.model.Session
import io.reactivex.Single
import retrofit2.http.GET


interface DroidconApiService {

    // start & end queries are always the same, so it's fine to hardcode them
    @GET(BuildConfig.FEED_API_URL)
    fun getSchedule(): Single<List<Session>>

}