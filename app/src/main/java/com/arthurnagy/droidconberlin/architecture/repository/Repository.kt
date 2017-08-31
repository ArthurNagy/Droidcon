package com.arthurnagy.droidconberlin.architecture.repository

import com.jakewharton.rxrelay2.PublishRelay

abstract class Repository<DataType, KeyType> : Source<DataType, KeyType> {

    protected val dataStream: PublishRelay<List<DataType>> = PublishRelay.create()

    fun stream() = dataStream

}