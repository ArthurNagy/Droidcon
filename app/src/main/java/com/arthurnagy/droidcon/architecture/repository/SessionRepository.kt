package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionRepository @Inject constructor(
        private val localSource: SessionLocalSource,
        private val remoteSource: SessionRemoteSource) : Repository<Session, String>() {

    private fun getAndSaveRemoteSessions(): Observable<List<Session>> = remoteSource.get().flatMap { sessions ->
        save(sessions)
    }

    private fun getAndCacheLocalSessions(): Observable<List<Session>> = localSource.get()
            .flatMap { sessions ->
                Observable.fromIterable(sessions)
                        .doOnNext { session -> cachedData.put(session.id, session) }
                        .toList().toObservable()
            }

    override fun get(): Observable<List<Session>> {
        if (cachedData.isNotEmpty()) {
            return Observable.fromIterable(cachedData.values)
                    .toList().toObservable()
                    .doOnNext(dataStream::onNext)
        }
        return Observable.concat(getAndCacheLocalSessions(), getAndSaveRemoteSessions())
                .firstOrError()
                .toObservable()
                .doOnNext(dataStream::onNext)
    }

    override fun refresh(): Observable<List<Session>> = getAndSaveRemoteSessions()
            .doOnNext(dataStream::onNext)

    override fun get(key: String): Observable<Session> = Observable.concat(localSource.get(key), remoteSource.get(key))
            .firstElement()
            .toObservable()

    override fun delete(data: Session): Observable<Boolean> = localSource.delete(data).doOnNext {
        cachedData.remove(data.id)
    }

    override fun update(data: Session): Observable<Session> = localSource.update(data).map { session ->
        cachedData.put(session.id, session)
        session
    }

    override fun save(data: Session): Observable<Session> = localSource.save(data).map { session ->
        cachedData.put(session.id, session)
        session
    }

    override fun save(data: List<Session>): Observable<List<Session>> = localSource.save(data).flatMap { sessions ->
        Observable.fromIterable(sessions).map { session ->
            cachedData.put(session.id, session)
            session
        }.toList().toObservable()
    }

}