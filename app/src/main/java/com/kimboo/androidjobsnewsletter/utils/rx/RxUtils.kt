package com.kimboo.androidjobsnewsletter.utils.rx

import io.reactivex.Observable
import io.reactivex.functions.Function
import okhttp3.ResponseBody
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Response

/**
 * Created by Agustin Tomas Larghi on 4/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

/**
 * rx.Observable extended function that knows how to subscribe a DataSourceSubscriber
 */
fun <MODEL> Observable<DataSource<MODEL>>.subscribe(dataSourceSubscriber: DataSourceSubscriber<MODEL>) {
    subscribe({onNextArg -> dataSourceSubscriber.onNext(onNextArg)},
            {onErrorThrowable -> dataSourceSubscriber.onError(onErrorThrowable)})
}

//TODO LAMBDA FUNC API
private fun <T> Observable<DataSource<T>>.subscribezzzzz(onResultNext: ((T) -> Unit), onSuccessNext: ((T) -> Unit)) {
    subscribe()
}


/**
 * Is a Subscriber that knows how to handle rx.Observables which emits DataSources objects
 */
open class DataSourceSubscriber<MODEL>: Subscriber<DataSource<MODEL>> {
    override fun onNext(dataSource: DataSource<MODEL>) {
        when(dataSource.state) {
            DataSource.SOURCE_HTTP_SUCCESS -> {
                onSuccessNext(dataSource.model!!)
            }
            DataSource.SOURCE_HTTP_NOT_MODIFIED -> {
                onNotModifiedNext(dataSource.model!!)
            }
        }
        onResultNext(dataSource.model!!)
    }

    open fun onResultNext(model: MODEL) {}
    open fun onSuccessNext(model: MODEL) {}
    open fun onNotModifiedNext(model: MODEL) {}

    override fun onError(t: Throwable?) {
    }

    override fun onSubscribe(s: Subscription?) {}

    override fun onComplete() {}
}

/**
 * Takes a server-side Retrofit response and wraps it into a DataSource
 */
fun <SERVER, RESPONSE : Response<SERVER>, MODEL> Observable<RESPONSE>.transformEntity(func: ((SERVER) -> MODEL)): Observable<DataSource<MODEL>> {
    return map(object: Function<RESPONSE, DataSource<MODEL>> {
        override fun apply(t: RESPONSE): DataSource<MODEL> {
            if (t.isSuccessful) {
                try {
                    when (t.code()) {
                        304 -> {
                            //Not modified
                            return DataSource(func(t.body()!!), DataSource.SOURCE_HTTP_NOT_MODIFIED)
                        } else -> {
                        //Any other success code
                        return DataSource(func(t.body()!!), DataSource.SOURCE_HTTP_SUCCESS)
                    }
                    }
                } catch (e: Exception) {
                    throw RuntimeException("Non-successful response");
                }
            } else {
                throw RuntimeException("Non-successful response");
            }
        }
    })
}

/**
 * Extended function that knows how to turn an HTML response into a Json response. Use this function if you want to perform webscrapping
 * over a website and turn that into a json response as if you were directly hitting a server endpoint.
 */
fun <SERVER> Observable<Response<ResponseBody>>.transformHtmlResponseIntoJson(func: ((Response<ResponseBody>) -> (Response<SERVER>))): Observable<Response<SERVER>> {
    return map { r -> func(r) }
}