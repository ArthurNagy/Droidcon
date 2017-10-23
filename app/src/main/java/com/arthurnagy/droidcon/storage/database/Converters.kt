package com.arthurnagy.droidcon.storage.database

import android.arch.persistence.room.TypeConverter
import com.arthurnagy.droidcon.model.Session
import java.util.*


class Converters {

    @TypeConverter
    fun timestamToDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return (date?.time)
    }

    @TypeConverter
    fun sessionRoomToString(room: Session.Room?): String? = room?.roomValue

    @TypeConverter
    fun stringToSessionRoom(stringRoom: String?): Session.Room? {
        return when (stringRoom) {
            Session.Room.LAMARR.roomValue -> Session.Room.LAMARR
            Session.Room.LOVELACE.roomValue -> Session.Room.LOVELACE
            Session.Room.TURING.roomValue -> Session.Room.TURING
            Session.Room.ZUSE.roomValue -> Session.Room.ZUSE
            else -> null
        }
    }

}