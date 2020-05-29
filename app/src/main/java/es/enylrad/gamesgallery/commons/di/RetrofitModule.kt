package es.enylrad.gamesgallery.commons.di

import android.content.Context
import com.readystatesoftware.chuck.ChuckInterceptor
import es.enylrad.gamesgallery.BuildConfig
import es.enylrad.gamesgallery.commons.network.ApiService
import es.enylrad.gamesgallery.commons.tag.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

val retrofitModule = module {
    factory {
        provideOkHttpClient(get())
    }
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
        .connectTimeout(5.toLong(), TimeUnit.SECONDS)
        .writeTimeout(100.toLong(), TimeUnit.SECONDS)
        .readTimeout(100.toLong(), TimeUnit.SECONDS)
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(
    ApiService::class.java
)