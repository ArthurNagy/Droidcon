package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.storage.database.SessionDao
import com.arthurnagy.droidcon.storage.database.SpeakerDao
import com.arthurnagy.droidcon.storage.database.TermDao
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionLocalSource @Inject constructor(
        private val sessionDao: SessionDao,
        private val termDao: TermDao,
        private val speakerDao: SpeakerDao) : Source<Session, String> {

    override fun get(): Observable<List<Session>> = sessionDao.getSessions()
            .filter { sessionsWithRelations -> sessionsWithRelations.isNotEmpty() }

    override fun refresh(): Observable<List<Session>> = Observable.empty()

    override fun get(key: String): Observable<Session> = sessionDao.getSession(key).toObservable()

    override fun delete(data: Session): Observable<Boolean> = Observable.fromCallable {
        val result = sessionDao.delete(data)
        result > 0
    }

    override fun update(data: Session): Observable<Session> = Observable.fromCallable {
        sessionDao.update(data)
    }.flatMap { get(data.id) }

    override fun save(data: Session): Observable<Session> = Observable.fromCallable {
        data.terms?.let {
            termDao.insertAll(*it.toTypedArray())
        }
        data.speakers?.let {
            speakerDao.insertAll(*it.toTypedArray())
        }
        sessionDao.insertSessions(data)
        data
    }

    override fun save(data: List<Session>): Observable<List<Session>> {
        return Observable.zip(sessionDao.getSavedSessionIds()
                .toObservable().onErrorResumeNext(Observable.empty<List<String>>()),
                Observable.just(data),
                BiFunction<List<String>, List<Session>, List<Session>> { savedSessionIds, sessions ->
                    sessions.forEach { session ->
                        session.isSaved = savedSessionIds.contains(session.id)
                    }
                    sessions.forEach { session ->
                        session.terms?.let { terms ->
                            termDao.insertAll(*terms.toTypedArray())
                        }
                        session.speakers?.let { speakers ->
                            speakerDao.insertAll(*speakers.toTypedArray())
                        }
                    }
                    sessionDao.insertSessions(*sessions.toTypedArray())
                    sessions
                })
    }

}