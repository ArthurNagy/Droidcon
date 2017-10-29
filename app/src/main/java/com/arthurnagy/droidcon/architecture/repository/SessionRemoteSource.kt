package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.DroidconApiService
import com.arthurnagy.droidcon.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRemoteSource @Inject constructor(
        private val apiService: DroidconApiService) : Source<Session, String> {

    // TODO: Find a better way to filter out duplicates from API
    private fun loadSessionsAndFilter(): Observable<List<Session>> = apiService.getSchedule()
            .flatMapObservable { sessions ->
                Observable.fromIterable(sessions
                        .associateBy { "${it.title}+++${it.room.roomValue}" }
                        .values).toList().toObservable()
            }

    override fun get(): Observable<List<Session>> = loadSessionsAndFilter()

    override fun refresh(): Observable<List<Session>> = loadSessionsAndFilter()

    override fun get(key: String): Observable<Session> = Observable.empty()

    override fun delete(data: Session): Observable<Boolean> = Observable.just(true)

    override fun update(data: Session): Observable<Session> = Observable.just(data)

    override fun save(data: Session): Observable<Session> = Observable.just(data)

    override fun save(data: List<Session>): Observable<List<Session>> = Observable.just(data)

}