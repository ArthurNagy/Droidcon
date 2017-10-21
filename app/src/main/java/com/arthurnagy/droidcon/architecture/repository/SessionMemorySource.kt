package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionMemorySource @Inject constructor() : Source<Session, String> {

    private val cachedSessions: MutableMap<String, Session> = LinkedHashMap()

    override fun get(): Observable<List<Session>>
            = Observable.just(ArrayList(cachedSessions.values))

    override fun get(key: String): Observable<Session>
            = Observable.just(cachedSessions[key])

    override fun refresh(): Observable<List<Session>> = Observable.empty()

    override fun delete(key: String): Observable<Boolean>
            = Observable.just(cachedSessions.remove(key) != null)

    override fun save(data: Session): Observable<Session> = Observable.just(data).map({ session ->
        cachedSessions.put(session.id, session)
        session
    })
}