package com.kimboo.androidjobsnewsletter.ui.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.kimboo.androidjobsnewsletter.R
import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.utils.inflate

/**
 * Created by Agustin Tomas Larghi on 4/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class MainAdapter(var callback: MainAdapterCallback? = null): RecyclerView.Adapter<MainAdapter.MainViewHolder>(), ListItemJobDetailViewCallback {

    //region Variables declaration
    var items: MutableList<JobDetail> = ArrayList()
    var pinnedItems: MutableList<JobDetail>  = ArrayList()
    //endregion

    //region ListItemJobDetailViewCallback implementation
    override fun onApplyClicked(url: String) {
        callback?.onApplyClicked(url)
    }

    override fun onPinnedItemClicked(jobDetail: JobDetail?, markedAsPinned: Boolean) {
        if (markedAsPinned) {
            pinnedItems.add(jobDetail!!)
        } else {
            pinnedItems.remove(jobDetail)
        }
        callback?.onPinnedItemClicked(jobDetail, markedAsPinned, pinnedItems.size)
    }
    //endregion

    //region Adapter methods declaration
    fun addAllItems(jobs: List<JobDetail>) {
        val originalPos = items.size
        items.addAll(jobs)
        notifyItemRangeInserted(originalPos, items.size - originalPos)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainViewHolder?, position: Int) {
        holder?.listItemJobDetailView?.item = items[position]
        holder?.listItemJobDetailView?.isMarkedAsPinned = pinnedItems.contains(items[position])
        holder?.listItemJobDetailView?.callback = this
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainViewHolder {
        return MainViewHolder(parent!!.inflate(R.layout.view_list_job_detail, false) as ListItemJobDetailView)
    }
    //endregion

    //region ViewHolder declaration
    class MainViewHolder(itemView: ListItemJobDetailView): RecyclerView.ViewHolder(itemView) {
        var listItemJobDetailView: ListItemJobDetailView = itemView
    }
    //endregion
}

//region Callback to view interface declaration
interface MainAdapterCallback {
    fun onApplyClicked(url: String)
    fun onPinnedItemClicked(jobDetail: JobDetail?, markedAsPinned: Boolean, size: Int)
}
//endregion