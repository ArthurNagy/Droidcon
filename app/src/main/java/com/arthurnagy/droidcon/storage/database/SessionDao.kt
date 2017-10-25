package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.*
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.model.Speaker
import com.arthurnagy.droidcon.model.Term
import io.reactivex.Observable
import io.reactivex.Single

@Dao
abstract class SessionDao {


    @Query("""SELECT * FROM ${Constants.TABLE_SESSION}
        WHERE id LIKE :id""")
    abstract fun getById(id: String): Single<Session>

    @Query("SELECT * FROM ${Constants.TABLE_SESSION}")
    abstract fun getAll(): Single<List<Session>>

    @Query("""SELECT * FROM ${Constants.TABLE_SPEAKER}
        INNER JOIN ${Constants.TABLE_SESSION_SPEAKER_RELATION} AS ss on ss.${Constants.ID_SPEAKER} = id
        AND ss.${Constants.ID_SESSION} = :sessionId""")
    abstract fun getSessionSpeakers(sessionId: String): Single<List<Speaker>>

    @Query("""SELECT * FROM ${Constants.TABLE_TERM}
        INNER JOIN ${Constants.TABLE_SESSION_TERM_RELATION} AS st on st.${Constants.ID_TERM} = id
        AND st.${Constants.ID_SESSION} = :sessionId""")
    abstract fun getSessionTerms(sessionId: String): Single<List<Term>>

    fun getSession(id: String): Single<Session> {
        return getById(id).flatMap { session ->
            getSessionSpeakers(session.id).map { speakers ->
                session.speakers = speakers
                session
            }.flatMap { sessionSpeaker ->
                getSessionTerms(sessionSpeaker.id).map { terms ->
                    sessionSpeaker.terms = terms
                    sessionSpeaker
                }
            }
        }
    }

    fun getSessions(): Observable<List<Session>> {
        return getAll().flatMapObservable { sessions ->
            Observable.fromIterable(sessions).flatMap { session ->
                getSessionSpeakers(session.id).map { speakers ->
                    session.speakers = speakers
                    session
                }.flatMapObservable { sessionSpeaker ->
                    getSessionTerms(sessionSpeaker.id).map { terms ->
                        sessionSpeaker.terms = terms
                        sessionSpeaker
                    }.toObservable()
                }
            }.toList().toObservable()
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(vararg sessions: Session): Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSessionSpeakers(sessionSpeakers: List<SessionSpeaker>): Array<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSessionTerms(sessionTerms: List<SessionTerm>): Array<Long>

    @Transaction
    open fun insertSessions(vararg sessions: Session) {
        insertAll(*sessions)
        val sessionSpeakers = sessions
                .filter { session -> session.speakers != null }
                .flatMap { session ->
                    session.speakers!!.map { speaker ->
                        SessionSpeaker(sessionId = session.id, speakerId = speaker.name)
                    }
                }
        val sessionTerms = sessions
                .filter { session -> session.terms != null }
                .flatMap { session ->
                    session.terms!!.map { term ->
                        SessionTerm(sessionId = session.id, termId = term.id)
                    }
                }
        insertSessionSpeakers(sessionSpeakers)
        insertSessionTerms(sessionTerms)
    }

    @Delete
    abstract fun delete(session: Session): Int


}