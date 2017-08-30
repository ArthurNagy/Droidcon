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
        return memorySource.get().flatMap { memorySessions ->
            if (memorySessions.isEmpty()) {
                remoteSource.get().flatMap { remoteSessions ->
                    Observable.fromIterable(remoteSessions)
                            .flatMap { session -> memorySource.save(session) }
                            .toList().toObservable()
                }
            } else {
                Observable.just(memorySessions)
            }
        }
    }

    override fun get(key: String): Observable<Session> = Observable.concat(memorySource.get(key),
            remoteSource.get(key))
            .firstElement()
            .toObservable()

    override fun delete(key: String): Observable<Boolean> {
        TODO()
    }

    override fun save(data: Session): Observable<Session> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}