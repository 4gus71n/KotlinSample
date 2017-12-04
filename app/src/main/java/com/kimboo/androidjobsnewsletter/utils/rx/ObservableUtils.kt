package com.kimboo.androidjobsnewsletter.utils.rx

import retrofit2.Response
import rx.Observable
import rx.functions.Func1

/**
 * Created by Agustin Tomas Larghi on 21/04/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

object ObservableUtils {

    //TODO IMPROVE ALL THIS WITH EXTENSION FUNCTIONS
    //TODO IMPROVE ALL THIS WITH EXTENSION FUNCTIONS
    //TODO IMPROVE ALL THIS WITH EXTENSION FUNCTIONS
    //TODO IMPROVE ALL THIS WITH EXTENSION FUNCTIONS
    //TODO IMPROVE ALL THIS WITH EXTENSION FUNCTIONS
    //TODO IMPROVE ALL THIS WITH EXTENSION FUNCTIONS

    /**
     * We use this method to conver server-side responses into local model objects.
     * @param transformer Is the hook that we use to convert the collection.
     */
    fun <SERVER, MODEL> mapResponses(transformer: Transformable<SERVER, MODEL>): Observable.Transformer<Response<SERVER>, DataSource<MODEL>> {
        return Observable.Transformer { responseObservable ->
            responseObservable
                    /**
                     * Filters the methods by the http errors.
                     * If the error code is 200 emits [DataSource] with the http code set
                     * to 200 and the  model set. Same if the code is 304. If something goes wrong
                     * directly jumps to the [rx.Subscriber.onError] sending a [ServerSideException]
                     * with the retrofit Response set.
                     */
                    /**
                     * Filters the methods by the http errors.
                     * If the error code is 200 emits [DataSource] with the http code set
                     * to 200 and the  model set. Same if the code is 304. If something goes wrong
                     * directly jumps to the [rx.Subscriber.onError] sending a [ServerSideException]
                     * with the retrofit Response set.
                     */
                    .compose(ObservableUtils.filterHttpErrors(transformer))
        }
    }

    /**
     * This method is helpful if we want to handle specific exception error flows. For example,
     * if we want to switch to some specific stream if a ServerSideException pops up, the only thing
     * that we have to do is:
     * `
     * ...
     * .onErrorResumeNext(ObservableUtils.whenExceptionIs(ServerSideException.class,
     * mDigitalPassesCache.getDigitalPassesByTeamSipid(teamSipid)))
     * .subscribe(new DefaultSubscriber<DigitalPasses>() {
     * ...
    </DigitalPasses>` *
     *
     * We can do the same for any other type of exception.
     */
    fun <T, E> whenExceptionIs(what: Class<E>, observable: Observable<T>): Func1<Throwable, Observable<T>> {
        return Func1 { t -> if (what.isInstance(t)) observable else Observable.error(t) }
    }

    /**
     * Takes the retrofit Response<BodyResponse> and wraps it in a DataSource. Since we dont care
     * about mapping the response, the Response object will be treated as a simple Object instance.
     * @return
    </BodyResponse> */
    fun defaultObjectMapping(): Observable.Transformer<Response<*>, DataSource<Any>> {
        return Observable.Transformer { retrofitObservable ->
            retrofitObservable.flatMap { o ->
                if (o.raw().networkResponse()!!.code() == 304) {
                    Observable.just(DataSource(o as Any, DataSource.SOURCE_HTTP_NOT_MODIFIED))
                } else if (o.isSuccessful) {
                    Observable.just(DataSource(o as Any, DataSource.SOURCE_HTTP_SUCCESS))
                } else {
                    Observable.error(ServerSideException(o))
                }
            }
        }
    }

    /**
     * Interface used to generate converters
     */
    interface Transformable<SERVER, MODEL> {
        fun transformServerToModel(serverResponseWrapper: SERVER): MODEL
    }

    fun <SERVER, MODEL> filterHttpErrors(transformable: Transformable<SERVER, MODEL>): Observable.Transformer<in Response<SERVER>, DataSource<MODEL>> {
        return Observable.Transformer { responseObservable ->
            responseObservable.flatMap { tResponse ->
                if (tResponse.code() == 304) {
                    val model = transformable.transformServerToModel(tResponse.body()!!)
                    Observable.just(DataSource(model, DataSource.SOURCE_HTTP_NOT_MODIFIED))
                } else if (tResponse.isSuccessful) {
                    val model = transformable.transformServerToModel(tResponse.body()!!)
                    Observable.just(DataSource(model, DataSource.SOURCE_HTTP_SUCCESS))
                } else {
                    Observable.error(ServerSideException(tResponse))
                }
            }
        }
    }

}
