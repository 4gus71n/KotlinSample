package com.kimboo.androidjobsnewsletter.utils.rx

import android.support.annotation.IntDef

/**
 * Created by Agustin Tomas Larghi on 17/5/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class DataSource<MODEL>(var model: MODEL?, @param:DataSourceState @field:DataSourceState @get:DataSourceState var state: Int) {

    @Retention()
    @IntDef(SOURCE_HTTP_NOT_MODIFIED.toLong(), SOURCE_HTTP_SUCCESS.toLong(), SOURCE_DATABASE.toLong(), SOURCE_MIXED.toLong())
    annotation class DataSourceState

    companion object {
        const val SOURCE_HTTP_NOT_MODIFIED = 304
        const val SOURCE_HTTP_SUCCESS = 200
        const val SOURCE_DATABASE = 666
        const val SOURCE_MIXED = 777
    }

}
