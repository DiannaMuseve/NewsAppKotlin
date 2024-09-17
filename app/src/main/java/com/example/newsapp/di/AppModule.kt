package com.example.newsapp.di


import android.content.Context
import androidx.room.Room
import com.example.newsapp.api.ArticleDao
import com.example.newsapp.api.NewsApiService
import com.example.newsapp.api.NewsDatabase
import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.NewsRepositoryImpl
import com.example.newsapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.CertificatePinner
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide the Room database
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_db"
        ).build()
    }

    // Provide the DAO
    @Singleton
    @Provides
    fun provideArticleDao(db: NewsDatabase): ArticleDao {
        return db.articleDao()
    }

    @Singleton
    @Provides
    fun providesNewsRepository(
        newsApiService: NewsApiService,
        appDAO: NewsDatabase
    ): NewsRepository {
        return NewsRepositoryImpl(newsApiService, appDAO)
    }


//    @Singleton
//    @Provides
//    fun providesAuthRepository() : NewsRepository {
//        return NewsRepositoryImpl()
//    }

    //  SSL pinning
    // secure security layer
    // authorization of using base url + endpoints =>  api
    val specs: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.RESTRICTED_TLS)
        .tlsVersions(TlsVersion.TLS_1_3,TlsVersion.TLS_1_2)
        .build()

    // privatekeys
    // public keys

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .certificatePinner(
                CertificatePinner.Builder()
                  .add("*.newsapi.org","sha256/nMqjbDWenPPz3XZ/kOv7V8tKAyrlLMZDFR2SuK1ufDs=")
                  .build())
            .addInterceptor(httpLoggingInterceptor)
            .connectionSpecs(listOf(specs))
            .build()
    }

    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }





    @Provides
    @Singleton
    fun providesRetrofit(
        test: OkHttpClient,
        converter: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.getStringBaseUrlDevelopment())
            .client(providesOkHttpClient(providesHttpLoggingInterceptor()))
            .addConverterFactory(converter)
            .build()
    }

    @Provides
    @Singleton
    fun providesNewsApiService(retrofit: Retrofit): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }





}
