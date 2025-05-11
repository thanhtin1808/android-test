package com.example.cubandroidtest.di

import com.example.cubandroidtest.repository.NewsRepositoryImpl
import com.example.cubandroidtest.BuildConfig
import com.example.cubandroidtest.data.remote.ApiService
import com.example.cubandroidtest.repository.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Named("BaseUrl")
    fun provideBaseUrl() = "https://newsapi.org"

    @Provides
    @Named("ApiKey")
    fun provideApiKey(): String = BuildConfig.API_KEY

    @Singleton
    @Provides
    fun provideOkHttpClient(@Named("ApiKey") apiKey: String): OkHttpClient {
        val headerInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val requestWithHeaders = original.newBuilder()
                .header("X-Api-Key", apiKey)
                .build()
            chain.proceed(requestWithHeaders)
        }

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(headerInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        @Named("BaseUrl") BASE_URL: String,
        okHttpClient: OkHttpClient,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideNewsRepository(
        apiService: ApiService
    ): NewsRepository {
        return NewsRepositoryImpl(apiService)
    }
}
