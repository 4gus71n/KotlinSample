package com.kimboo.androidjobsnewsletter.utils.rx

import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription

/**
 * Created by Agustin Tomas Larghi on 2/7/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
open class DefaultSubscriber<T> : Subscriber<T> {

    override fun onSubscribe(s: Subscription?) {

    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {

    }

    override fun onNext(o: T) {

    }

}
