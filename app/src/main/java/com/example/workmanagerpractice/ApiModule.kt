package com.example.workmanagerpractice

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    fun provideSharedPref(@ApplicationContext context: Context):SharedPreferences{
        return context.getSharedPreferences("user_data",0)
    }



    @Provides
    fun provideOkHttpClient(
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun provideGsonConvertorFactory():GsonConverterFactory{
        return GsonConverterFactory.create()
    }


        @Provides
        fun provideRetrofit(client: OkHttpClient,
        converter:GsonConverterFactory):Retrofit{
            return Retrofit.Builder()
                .client(client)
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(converter)
                .build()
        }
    @Provides
    fun provideWebServices(retrofit: Retrofit): WebServices {
        return retrofit.create(WebServices::class.java)
    }
}