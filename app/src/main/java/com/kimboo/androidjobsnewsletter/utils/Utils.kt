package com.kimboo.androidjobsnewsletter.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Agustin Tomas Larghi on 4/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */

/**
 * Lets a parent view inflate its own children
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}