package com.example.kweaitems.webservices

import retrofit2.Response

/** In interface for asynchronous executions. */
interface IExecute<T> {

    /** The callback to be executed. */
    fun run(result : Response<T>?, t : Throwable?)

}