package com.arthurnagy.droidconberlin.architecture.repository

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor

abstract class Repository<DataType, KeyType> : Source<DataType, KeyType> {

    protected val dataStream: FlowableProcessor<List<DataType>> = PublishProcessor.create()

    fun stream(): Flowable<List<DataType>> = dataStream

}