package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.storage.database.SessionDao
import com.arthurnagy.droidcon.storage.database.SpeakerDao
import com.arthurnagy.droidcon.storage.database.TermDao
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionLocalSource @Inject constructor(
        private val sessionDao: SessionDao,
        private val termDao: TermDao,
        private val speakerDao: SpeakerDao) : Source<Session, String> {

    override fun get(): Observable<List<Session>> = sessionDao.getAll()
            .filter { sessionsWithRelations -> sessionsWithRelations.isNotEmpty() }
            .toObservable()

    override fun refresh(): Observable<List<Session>> = Observable.empty()

    override fun get(key: String): Observable<Session> = sessionDao.getById(key).toObservable()

    override fun delete(data: Session): Observable<Boolean> = Observable.fromCallable {
        val result = sessionDao.delete(data)
        result > 0
    }

    override fun save(data: Session): Observable<Session> = Observable.fromCallable {
        sessionDao.insertAll(data)
        //TODO insert into relation table
//        data.terms?.forEach { it.sessionId = data.id }
        termDao.insertAll(*data.terms.orEmpty().toTypedArray())
        //TODO insert into relation table
//        data.speakers?.forEach { it.sessionId = data.id }
        speakerDao.insertAll(*data.speakers.orEmpty().toTypedArray())
        data
    }

}