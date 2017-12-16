package com.kimboo.androidjobsnewsletter.ui.main

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.text.Html
import android.util.AttributeSet
import android.widget.FrameLayout
import com.kimboo.androidjobsnewsletter.R
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
    var isMarkedAsPinned: Boolean = false
        set(value) {
            field = value
            if (value) {
                viewListJobDetailPinIcon.setImageResource(R.drawable.ic_pinon)
            } else {
                viewListJobDetailPinIcon.setImageResource(R.drawable.ic_pinoff)
            }
        }

    var callback: ListItemJobDetailViewCallback? = null

    var item: JobDetail? = null
        set(value) {
            field = value
            viewListJobDetailTitle.text = value?.title
            viewListJobDetailDescription.text = Html.fromHtml(value?.description)
            viewListJobDetailApplyButton.setOnClickListener { callback?.onApplyClicked(value!!.url) }
            viewListJobDetailPinIcon.setOnClickListener({ onPinnedItemClicked() })
        }
    //endregion

    //region Methods declaration

    private fun onPinnedItemClicked() {
        isMarkedAsPinned = !isMarkedAsPinned
        callback?.onPinnedItemClicked(item, isMarkedAsPinned)

    }

    //endregion

}

//region Callback to adapter interface declaration
interface ListItemJobDetailViewCallback {
    fun onApplyClicked(url: String)
    fun onPinnedItemClicked(jobDetail: JobDetail?, markedAsPinned: Boolean)
}
//endregion
