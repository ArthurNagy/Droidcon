package com.arthurnagy.droidconberlin.architecture.repository

import io.reactivex.Observable


interface Source<DataType, KeyType> {

    fun get(): Observable<List<DataType>>

    fun refresh(): Observable<List<DataType>>

    fun get(key: KeyType): Observable<DataType>

    fun delete(key: KeyType): Observable<Boolean>

    fun save(data: DataType): Observable<DataType>

}