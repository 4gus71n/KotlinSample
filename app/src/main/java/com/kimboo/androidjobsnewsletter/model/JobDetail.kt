package com.kimboo.androidjobsnewsletter.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class JobDetail(var title: String = "", var description: String = "", var url: String = "") : Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JobDetail> {
        override fun createFromParcel(parcel: Parcel): JobDetail {
            return JobDetail(parcel)
        }

        override fun newArray(size: Int): Array<JobDetail?> {
            return arrayOfNulls(size)
        }
    }

}
