package com.arthurnagy.droidconberlin.repository

import com.arthurnagy.droidconberlin.architecture.repository.Repository
import com.arthurnagy.droidconberlin.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor(
        private val memorySource: SessionMemorySource,
        private val remoteSource: SessionRemoteSource
) : Repository<Session, String>() {

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