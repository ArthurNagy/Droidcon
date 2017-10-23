package com.arthurnagy.droidcon.architecture.repository

import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.storage.database.SessionDao
import com.arthurnagy.droidcon.storage.database.SessionWithRelations
import com.arthurnagy.droidcon.storage.database.SpeakerDao
import com.arthurnagy.droidcon.storage.database.TermDao
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionLocalSource @Inject constructor(
        private val sessionDao: SessionDao,
        private val termDao: TermDao,
        private val speakerDao: SpeakerDao) : Source<Session, String> {

    override fun get(): Observable<List<Session>> = sessionDao.getAll()
            .toObservable()
            .flatMapIterable { sessionsWithRelations ->
                sessionsWithRelations
                        .map { sessionWithRelations ->
                            sessionWithRelations.toSession()
                        }
            }.toList().toObservable()

    override fun refresh(): Observable<List<Session>> = Observable.empty()

    override fun get(key: String): Observable<Session> = sessionDao.getById(key)
            .toObservable()
            .map { sessionWithRelations ->
                sessionWithRelations.toSession()
            }

    override fun delete(data: Session): Completable = Completable.fromAction { sessionDao.delete(data) }

    override fun save(data: Session): Observable<Session> = Observable.just(data)
            .doOnSubscribe {
                sessionDao.insertAll(data)
                data.terms?.let { termDao.insertAll(*it.toTypedArray()) }
                data.speakers?.let { speakerDao.insertAll(*it.toTypedArray()) }
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