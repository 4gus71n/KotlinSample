package com.kimboo.androidjobsnewsletter.di.module

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kimboo.androidjobsnewsletter.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * Created by Agustin on 01/11/2016.
 */
@Module
class NetworkModule(private val mHost: String = BuildConfig.HOST) {

    companion object {
        private val ENABLE_OKHTTP_CACHE = true
        private val SIZE_OF_CACHE = (10 * 1024 * 1024).toLong() // 10 MB
    }

    @Provides
    @Singleton
    fun provideCache(context: Context) = okhttp3.Cache(File(context.cacheDir, "http"), SIZE_OF_CACHE)


    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create()
    }

    @Provides
    @Singleton
    @Named("cacheInterceptor")
    protected fun provideCacheInterceptor(): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                    .header("Cache-Control", "public, max-age=60")
                    .build()
            chain.proceed(request)
        }
    }

    @Provides
    @Singleton
    protected fun provideOkHttpClient(cache: okhttp3.Cache,
                                      @Named("cacheInterceptor") cacheInterceptor: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (ENABLE_OKHTTP_CACHE) {
            builder.cache(cache)
        }

        try {
            setUnsafeSslCertificates(builder)
        } catch (e: Exception) {
            throw RuntimeException("Couldn't build SSL certificates")
        }

        builder.connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)

        return builder.build()
    }

    @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
    private fun setUnsafeSslCertificates(builder: OkHttpClient.Builder) {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
            @Throws(CertificateException::class)
            override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            @Throws(CertificateException::class)
            override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                return arrayOf()
            }
        })

        // Install the all-trusting trust manager
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())
        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory

        builder.sslSocketFactory(sslSocketFactory)
        builder.hostnameVerifier { hostname, session -> true }
    }

    @Provides
    @Singleton
    protected fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .baseUrl(mHost)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()
    }

}
