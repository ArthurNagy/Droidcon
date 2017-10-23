package com.arthurnagy.droidcon.architecture.repository

import io.reactivex.Completable
import io.reactivex.Observable


interface Source<DataType, KeyType> {

    fun get(): Observable<List<DataType>>

    fun get(key: KeyType): Observable<DataType>

    fun refresh(): Observable<List<DataType>>

    fun delete(data: DataType): Completable

    fun save(data: DataType): Observable<DataType>

}