package com.arthurnagy.droidconberlin.repository

import com.arthurnagy.droidconberlin.architecture.repository.Repository
import com.arthurnagy.droidconberlin.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


// TODO implement local database source for saving data and my agenda sessions
@Singleton
class SessionRepository @Inject constructor(
        private val memorySource: SessionMemorySource,
        private val remoteSource: SessionRemoteSource) : Repository<Session, String>() {

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
            }.doOnNext(dataStream::onNext)
        }
    }

    override fun refresh(): Observable<List<Session>> {
        return remoteSource.get().flatMap { remoteSessions ->
            Observable.fromIterable(remoteSessions)
                    .flatMap { session -> memorySource.save(session) }
                    .toList().toObservable()
        }.doOnNext(dataStream::onNext)
    }

    override fun get(key: String): Observable<Session> = Observable.concat(memorySource.get(key),
            remoteSource.get(key))
            .firstElement()
            .toObservable()

    override fun delete(key: String): Observable<Boolean> = memorySource.delete(key)

    override fun save(data: Session): Observable<Session> = memorySource.save(data)

}