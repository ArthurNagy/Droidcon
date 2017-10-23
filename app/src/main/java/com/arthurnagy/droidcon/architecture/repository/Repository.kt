package com.arthurnagy.droidcon.architecture.repository

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

abstract class Repository<DataType, KeyType> : Source<DataType, KeyType> {

    protected val dataStream: FlowableProcessor<List<DataType>> = PublishProcessor.create()
    protected val cachedData: MutableMap<KeyType, DataType> = LinkedHashMap()

    fun stream(): Flowable<List<DataType>> = dataStream

}