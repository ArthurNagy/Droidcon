package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation
import com.arthurnagy.droidcon.model.Session
import com.arthurnagy.droidcon.model.Speaker
import com.arthurnagy.droidcon.model.Term


class SessionWithRelations constructor(@Embedded var session: Session) {

    @Relation(parentColumn = "id", entityColumn = "session_id")
    var speakers: List<Speaker>? = null
    @Relation(parentColumn = "id", entityColumn = "session_id")
    var terms: List<Term>? = null

}