package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.DroidconApiService
import com.arthurnagy.droidcon.model.Session
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRemoteSource @Inject constructor(
        private val apiService: DroidconApiService) : Source<Session, String> {

    override fun get(): Observable<List<Session>> = apiService.getSchedule().toObservable()

    override fun refresh(): Observable<List<Session>> = apiService.getSchedule().toObservable()

    override fun get(key: String): Observable<Session> = Observable.empty()

    override fun delete(data: Session): Completable = Completable.complete()

    override fun save(data: Session): Observable<Session> = Observable.just(data)

}