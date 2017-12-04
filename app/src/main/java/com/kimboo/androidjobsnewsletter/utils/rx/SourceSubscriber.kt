package com.kimboo.androidjobsnewsletter.utils.rx

import android.database.SQLException
import android.util.Log

import java.io.IOException

/**
 * Created by Agustin Tomas Larghi on 17/5/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
open class SourceSubscriber<MODEL> : DefaultSubscriber<DataSource<MODEL>>() {

    override fun onError(e: Throwable) {
        Log.e(TAG, "onError", e)
        if (e is SQLException) {
            onSqlError(e)
        } else if (e is ServerSideException) {
            onServerSideError(e)
        } else if (e is IOException) {
            onNoInternetError(e)
        } else {
            onUnhandledError(e)
        }
        onResultError(e)
    }

    /**
     * Triggered if we call a Repository method without internet connection
     * @param e
     */
    open fun onNoInternetError(e: Throwable) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong, always. Hook from this method if you don't care about
     * handling specific error conditions.
     * @param e
     */
    open fun onResultError(e: Throwable) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong and the exception is not in the "instanceof" switch of the
     * [.onError]. We are also logging the exception on crashlytics if this happens.
     * @param e
     */
    open fun onUnhandledError(e: Throwable) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong on the server side.
     * @param e
     */
    open fun onServerSideError(e: Throwable) {
        //Optional to implement
    }

    /**
     * Executed when something goes wrong while doing some query in the database.
     * @param e
     */
    open fun onSqlError(e: SQLException) {
        //Optional to implement
    }

    override fun onNext(modelDataSource: DataSource<MODEL>) {
        when (modelDataSource.state) {
            DataSource.SOURCE_DATABASE -> onDatabaseNext(modelDataSource.model!!)
            DataSource.SOURCE_HTTP_NOT_MODIFIED -> onNotModifiedNext(modelDataSource.model!!)
            DataSource.SOURCE_HTTP_SUCCESS -> onSuccessNext(modelDataSource.model!!)
        }
        onResultNext(modelDataSource.model!!)
    }

    /**
     * Executed when fetching data, always.
     * @param model
     */
    open fun onResultNext(model: MODEL) {
        //Optional to implement
    }

    /**
     * Executed when fetching data from the server-side, only if the request returned 200
     * @param model
     */
    open fun onSuccessNext(model: MODEL) {
        //Optional to implement
    }

    /**
     * Executed when fetching data from the server-side, only if the request returned 304
     * @param model
     */
    open fun onNotModifiedNext(model: MODEL) {
        //Optional to implement
    }

    /**
     * Executed when fetching data from the cache database
     * @param model
     */
    open fun onDatabaseNext(model: MODEL) {
        //Optional to implement
    }

    override fun onCompleted() {

    }

    companion object {
        private val TAG = SourceSubscriber::class.java.simpleName
    }
}
