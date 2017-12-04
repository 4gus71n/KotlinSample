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
class MainAdapter(): RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var items: MutableList<JobDetail> = ArrayList()

    fun setAllItems(jobs: List<JobDetail>) {
        items.clear()
        items.addAll(jobs)
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MainViewHolder?, position: Int) {
        holder?.updateView(items.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainViewHolder {
        return MainViewHolder(parent!!.inflate(R.layout.view_list_job_detail, false) as ListItemJobDetailView)
    }

    class MainViewHolder(itemView: ListItemJobDetailView): RecyclerView.ViewHolder(itemView) {

        var listItemJobDetailView: ListItemJobDetailView = itemView

        fun updateView(jobDetail: JobDetail) {
            listItemJobDetailView.updateView(jobDetail)
        }

    }
}