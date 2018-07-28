package ru.arturvasilov.multimodulemovie.network

import android.content.Context
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.arturvasilov.multimodulemovie.core.NetworkScope
import java.util.concurrent.TimeUnit

internal const val CONNECT_TIMEOUT = 15L
internal const val READ_TIMEOUT = 30L
internal const val WRITE_TIMEOUT = 30L

interface NetworkComponentProvider {
    fun provideNetworkComponent(): NetworkComponent
}

fun Context.findNetworkComponent(): NetworkComponent {
    if (applicationContext is NetworkComponentProvider) {
        return (applicationContext as NetworkComponentProvider).provideNetworkComponent()
    }
    throw NullPointerException("Failed to find provider for NetworkComponent")
}

@NetworkScope
@Component(modules = [(NetworkModule::class)])
interface NetworkComponent {
    fun provideRetrofit(): Retrofit
}

@Module
class NetworkModule {

    @NetworkScope
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(ApiKeyInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()

    @NetworkScope
    @Provides
    fun provideRetrofit(client: OkHttpClient) =
            Retrofit.Builder().client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.API_ENDPOINT)
                    .build()!!
}