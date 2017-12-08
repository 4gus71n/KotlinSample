package com.kimboo.androidjobsnewsletter.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kimboo.androidjobsnewsletter.NewsletterApplication
import com.kimboo.androidjobsnewsletter.R
import com.kimboo.androidjobsnewsletter.model.JobDetail
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject


/**
 * Created by Agustin Tomas Larghi on 2/12/2017.
 * Email: agustin.tomas.larghi@gmail.com
 */
class MainFragment: Fragment(), MainView, MainAdapterCallback  {

    //region Static code declaration
    companion object {
        val TAG = MainFragment.javaClass.simpleName;

        fun newInstance(): MainFragment {
            val f = MainFragment()
            val args = Bundle()
            f.arguments = args
            return f
        }
    }
    //endregion

    //region Variables declaration
    @Inject
    lateinit var presenter: MainPresenter

    var adapter: MainAdapter = MainAdapter(this)
    //endregion

    //region MainAdapterCallback implementation
    override fun onApplyClicked(url: String) {
        val uri = Uri.parse(url)
        val i = Intent(Intent.ACTION_VIEW, uri)
        startActivity(i)
    }
    //endregion

    //region MainView implementation
    override fun onShowError(error: String) {
        Snackbar.make(view!!, error, Snackbar.LENGTH_LONG).show()
    }

    override fun onJobsFetched(jobs: List<JobDetail>) {
        adapter.setAllItems(jobs)
    }

    override fun onHideLoader() {
        fragmentMainSwipeRefreshLayout.isRefreshing = false
    }

    override fun onShowLoader() {
        fragmentMainSwipeRefreshLayout.isRefreshing = true
    }
    //endregion

    //region Fragment lifecycle methods declaration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NewsletterApplication.viewInjector.inject(this)

        arguments?.let { presenter.onViewCreated(this , arguments) }
        savedInstanceState?.let { presenter.onViewCreated(this, savedInstanceState) }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_main, container, false);
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentMainSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        fragmentMainSwipeRefreshLayout.setOnRefreshListener { presenter.fetchJobs() }

        fragmentMainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragmentMainRecyclerView.adapter = adapter

        presenter.fetchJobs() //TODO Call getJobs() here
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        presenter.onSaveInstance(outState!!)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
    //endregion



}