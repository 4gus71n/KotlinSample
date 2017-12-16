package com.kimboo.androidjobsnewsletter.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Html
import android.text.Spanned
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

    override fun onPinnedItemClicked(jobDetail: JobDetail?, markedAsPinned: Boolean, pinnedItems: Int) {
        fragmentMainBadgeImageView.badgeValue = pinnedItems
        fragmentMainBadgeImageView.isEnabled = pinnedItems > 0
    }

    //endregion

    //region MainView implementation
    override fun onShowError(error: String) {
        Snackbar.make(view!!, error, Snackbar.LENGTH_LONG).show()
    }

    override fun onJobsFetched(jobs: List<JobDetail>) {
        adapter.addAllItems(jobs)
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

        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(fragmentMainToolbar)

        fragmentMainBadgeImageView.setOnClickListener({ onBadgeImageViewClicked() })
        fragmentMainBadgeImageView.isEnabled = false

        fragmentMainSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
        fragmentMainSwipeRefreshLayout.setOnRefreshListener { presenter.fetchJobs() }

        fragmentMainRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        fragmentMainRecyclerView.adapter = adapter

        presenter.fetchJobs()
    }

    private fun onBadgeImageViewClicked() {
        val sharingIntent = Intent(Intent.ACTION_SEND)
        sharingIntent.type = "text/html"
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, fromHtmlCompat(getSharedLinksText()))
        startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_using)))
    }

    //TODO Convert into extended function
    fun fromHtmlCompat(html: String): Spanned {
        val result: Spanned
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            result = Html.fromHtml(html)
        }
        return result
    }

    private fun getSharedLinksText(): String {
        var htmlMessage = ""

        htmlMessage += "<ul>"
        for (pinnedJobDetail in adapter.pinnedItems) {
            htmlMessage += "<li><p>\"${pinnedJobDetail.title}\" (${pinnedJobDetail.url})</p></li>"
        }
        htmlMessage += "</ul>"

        return htmlMessage
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