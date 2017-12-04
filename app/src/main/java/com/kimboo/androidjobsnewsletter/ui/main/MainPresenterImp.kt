package com.kimboo.androidjobsnewsletter.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import com.kimboo.androidjobsnewsletter.R
import com.kimboo.androidjobsnewsletter.interactors.GetJobs
import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.ui.BasePresenter

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class MainPresenterImp(context: Context, var getJobs: GetJobs) : BasePresenter<MainView>(context), MainPresenter, GetJobs.Callback {

    //region Static code declaration
    companion object {
        const val ARG_BUNDLE_JOB_LIST: String = "arg_bundle_job_list"
    }
    //endregion

    //region GetJobs.Callback implementation
    override fun onJobsFetched(jobs: List<JobDetail>) {
        jobsList = jobs
        view?.onJobsFetched(jobs)
    }

    override fun onErrorFetchingJobs() {
        view?.onShowError(getString(R.string.error_no_jobs))
    }
    //endregion

    //region Variables declaration
    var jobsList: List<JobDetail> = ArrayList()
    //endregion

    override fun onViewCreated(view: MainView, args: Bundle) {
        super.onViewCreated(view, args)
        if (args.containsKey(ARG_BUNDLE_JOB_LIST)) jobsList = args.getParcelableArrayList(ARG_BUNDLE_JOB_LIST)
    }

    override fun onSaveInstance(args: Bundle?) {
        args?.putParcelableArrayList(ARG_BUNDLE_JOB_LIST, jobsList as ArrayList<Parcelable>)
    }

    override fun fetchJobs() {
        getJobs.executeFromApi(this)
    }

    override fun getJobs() {
        getJobs.executeFromApi(this)
    }
}
