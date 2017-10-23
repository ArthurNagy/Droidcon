package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionRepository @Inject constructor(
        private val localSource: SessionLocalSource,
        private val remoteSource: SessionRemoteSource) : Repository<Session, String>() {

    override fun get(): Observable<List<Session>> {
        if (cachedData.isNotEmpty()) {
            return Observable.fromIterable(cachedData.values)
                    .toList().toObservable()
                    .doOnNext(dataStream::onNext)
        }
        val localSessions = getAndCacheLocalSessions()
        val remoteSessions = getAndSaveRemoteSessions()

        return Observable.concat(localSessions, remoteSessions)
                .filter { sessions -> !sessions.isEmpty() }
                .firstOrError()
                .toObservable()
                .doOnNext(dataStream::onNext)
    }

    override fun refresh(): Observable<List<Session>> = getAndSaveRemoteSessions()
            .doOnNext(dataStream::onNext)

    private fun getAndSaveRemoteSessions(): Observable<List<Session>> = remoteSource.get()
            .flatMap { sessions ->
                Observable.fromIterable(sessions)
                        .flatMap { session ->
                            cachedData.put(session.id, session)
                            localSource.save(session)
                        }.toList().toObservable()
            }

    private fun getAndCacheLocalSessions(): Observable<List<Session>> = localSource.get()
            .flatMap { sessions ->
                Observable.fromIterable(sessions)
                        .doOnNext { session -> cachedData.put(session.id, session) }
                        .toList().toObservable()
            }

    override fun get(key: String): Observable<Session> = Observable.concat(localSource.get(key), remoteSource.get(key))
            .firstElement()
            .toObservable()

    override fun delete(data: Session): Observable<Boolean> = localSource.delete(data)

    override fun save(data: Session): Observable<Session> = localSource.save(data)

}