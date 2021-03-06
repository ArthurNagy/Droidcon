package com.arthurnagy.droidcon.architecture.repository

import io.reactivex.Observable


interface Source<DataType, KeyType> {

    fun get(): Observable<List<DataType>>

    fun get(key: KeyType): Observable<DataType>

    fun refresh(): Observable<List<DataType>>

    fun delete(data: DataType): Observable<Boolean>

    fun update(data: DataType): Observable<DataType>

    fun save(data: DataType): Observable<DataType>

    fun save(data: List<DataType>): Observable<List<DataType>>

}