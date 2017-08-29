package com.arthurnagy.droidconberlin.repository

import com.arthurnagy.droidconberlin.DroidconApiService
import com.arthurnagy.droidconberlin.architecture.repository.Source
import com.arthurnagy.droidconberlin.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRemoteSource @Inject constructor(
        private val apiService: DroidconApiService) : Source<Session, String> {

    override fun get(): Observable<List<Session>> {
        TODO()
    }

    override fun get(key: String): Observable<Session> {
        TODO()
    }

    override fun delete(key: String): Observable<Boolean> {
        TODO()
    }
}