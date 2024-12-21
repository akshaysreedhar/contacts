package com.akzhey.contacts.di

import android.content.Context
import androidx.room.Room
import com.akzhey.contacts.common.Constants.BASE_URL
import com.akzhey.contacts.data.local.db.NumberDao
import com.akzhey.contacts.data.local.db.NumberDatabase
import com.akzhey.contacts.data.remote.RandomNumberApi
import com.akzhey.contacts.data.remote.interceptors.HeaderInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofitApi(httpClient: OkHttpClient): RandomNumberApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RandomNumberApi::class.java)
    }

    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient {
        val headerInterceptor = HeaderInterceptor()
        val loggingInterceptor = HttpLoggingInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun providesRoomDatabase(@ApplicationContext context: Context): NumberDatabase {
        return Room.databaseBuilder(context, NumberDatabase::class.java, "number-db").build()
    }

    @Provides
    @Singleton
    fun providesNumberDao(numberDatabase: NumberDatabase): NumberDao {
        return numberDatabase.numberDao()
    }
}