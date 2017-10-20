package com.arthurnagy.droidcon.injection

import com.arthurnagy.droidcon.BuildConfig
import com.arthurnagy.droidcon.DroidconApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object DroidconApiModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").create()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                        .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE))
                .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.DROIDCON_URL)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideDroidconApiService(retrofit: Retrofit): DroidconApiService {
        return retrofit.create(DroidconApiService::class.java)
    }

}