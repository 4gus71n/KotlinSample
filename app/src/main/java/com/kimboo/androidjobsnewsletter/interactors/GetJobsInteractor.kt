package com.kimboo.androidjobsnewsletter.interactors

import com.kimboo.androidjobsnewsletter.model.JobDetail
import com.kimboo.androidjobsnewsletter.repository.JobsNetworkRepository
import com.kimboo.androidjobsnewsletter.utils.rx.DataSourceSubscriber
import com.kimboo.androidjobsnewsletter.utils.rx.subscribe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class GetJobsInteractor(val repository: JobsNetworkRepository) : GetJobs {

    override fun executeFromApi(callback: GetJobs.Callback) {
        Observable.merge(repository.getJobsFromAndroidIo(), repository.getJobsFromRemoteIo(), repository.getJobsFromRemotelyAwesome())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(object: DataSourceSubscriber<List<JobDetail>>() {
                override fun onResultNext(model: List<JobDetail>) {
                    callback.onJobsFetched(model)
                }

                override fun onError(t: Throwable?) {
                    callback.onErrorFetchingJobs()
                }
            })
    }

}


