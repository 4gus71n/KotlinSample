package com.kimboo.androidjobsnewsletter.ui.main

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.FrameLayout
import com.kimboo.androidjobsnewsletter.model.JobDetail
import kotlinx.android.synthetic.main.view_list_job_detail.view.*

/**
 * Created by Agustin Tomas Larghi on 4/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class ListItemJobDetailView : FrameLayout {

    //region Constructors declaration
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {}
    //endregion

    //region Variables declaration
    var callback: ListItemJobDetailViewCallback? = null

    var item: JobDetail? = null
        set(value) {
            viewListJobDetailTitle.text = value?.title
            viewListJobDetailDescription.text = value?.description
            viewListJobDetailApplyButton.setOnClickListener { callback?.onApplyClicked(item!!.url) }
        }
    //endregion

}

//region Callback to adapter interface declaration
interface ListItemJobDetailViewCallback {
    fun onApplyClicked(url: String)
}
//endregion
