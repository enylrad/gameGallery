package es.enylrad.gamesgallery.core.di

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import es.enylrad.gamesgallery.BuildConfig
import es.enylrad.gamesgallery.core.api.IGDBService
import es.enylrad.gamesgallery.core.constants.BASE_URL
import es.enylrad.gamesgallery.core.constants.CONNECTION_TIME_OUT
import es.enylrad.gamesgallery.core.constants.READ_TIME_OUT
import es.enylrad.gamesgallery.core.constants.WRITE_TIME_OUT
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    factory { provideOkHttpClient(get()) }
    factory { provideApiService(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(
    context: Context
): OkHttpClient {
    return OkHttpClient()
        .newBuilder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .addInterceptor(ChuckInterceptor(context))
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("user-key", BuildConfig.API_CREDENTIALS)
                .build()
            chain.proceed(request)
        }
        .connectTimeout(CONNECTION_TIME_OUT.toLong(), TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIME_OUT.toLong(), TimeUnit.SECONDS)
        .readTimeout(READ_TIME_OUT.toLong(), TimeUnit.SECONDS)
        .build()
}

fun provideApiService(retrofit: Retrofit): IGDBService = retrofit.create(
    IGDBService::class.java
)