package com.kimboo.androidjobsnewsletter.utils.rx

import retrofit2.Response

/**
 * Created by Agustin Tomas Larghi on 21/04/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

class ServerSideException : Throwable {
    val response: Response<*>?

    constructor(tResponse: Response<*>) {
        response = tResponse
    }

    constructor(e: Throwable) : super(e) {
        response = null
    }

    constructor() : super() {
        response = null
    }
}
