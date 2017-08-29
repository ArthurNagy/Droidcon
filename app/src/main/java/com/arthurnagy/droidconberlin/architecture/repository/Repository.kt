package com.arthurnagy.droidconberlin.architecture.repository

import io.reactivex.subjects.PublishSubject

abstract class Repository<DataType, KeyType> : Source<DataType, KeyType> {

    protected val dataStream: PublishSubject<List<DataType>> = PublishSubject.create()

    fun stream() = dataStream

}