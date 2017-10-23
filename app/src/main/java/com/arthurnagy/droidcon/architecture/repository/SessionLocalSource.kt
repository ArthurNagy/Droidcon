package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.storage.database.SessionDao
import com.arthurnagy.droidcon.storage.database.SessionWithRelations
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
            .flatMapObservable { sessionsWithRelations ->
                Observable.fromIterable(sessionsWithRelations)
                        .map { sessionWithRelation -> sessionWithRelation.toSession() }
            }.toList()
            .toObservable()


    override fun refresh(): Observable<List<Session>> = Observable.empty()

    override fun get(key: String): Observable<Session> = sessionDao.getById(key)
            .toObservable()
            .map { sessionWithRelations ->
                sessionWithRelations.toSession()
            }

    override fun delete(data: Session): Observable<Boolean> = Observable.fromCallable {
        val result = sessionDao.delete(data)
        result > 0
    }

    override fun save(data: Session): Observable<Session> = Observable.fromCallable {
        sessionDao.insertAll(data)
        termDao.insertAll(*data.terms.orEmpty().toTypedArray())
        speakerDao.insertAll(*data.speakers.orEmpty().toTypedArray())
        data
    }

    private fun SessionWithRelations.toSession() =
            Session(id = this.session.id,
                    title = this.session.title,
                    url = this.session.url,
                    room = this.session.room,
                    startDate = this.session.startDate,
                    endDate = this.session.endDate,
                    description = this.session.description,
                    speakers = this.speakers,
                    terms = this.terms,
                    isSaved = this.session.isSaved)

}