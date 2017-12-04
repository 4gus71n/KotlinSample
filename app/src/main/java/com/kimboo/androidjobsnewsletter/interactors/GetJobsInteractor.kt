package com.kimboo.androidjobsnewsletter.interactors

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepository
import com.kimboo.androidjobsnewsletter.utils.rx.SourceSubscriber
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class GetJobsInteractor(val repository: JobsNetworkRepository) : GetJobs {

    override fun executeFromApi(callback: GetJobs.Callback) {
        repository.getJobs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                //TODO IMPROVE THIS WITH LAMBDAS
                .subscribe(object: SourceSubscriber<List<JobDetail>>() {
                    override fun onResultNext(result: List<JobDetail>) {
                        callback.onJobsFetched(result)
                    }

                    override fun onResultError(e: Throwable) {
                        callback.onErrorFetchingJobs()
                    }
                })

    }

}
