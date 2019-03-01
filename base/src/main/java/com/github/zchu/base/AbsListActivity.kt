package com.github.zchu.base

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.zchu.listing.AbsListingModel
import com.github.zchu.listing.ListingObserver

abstract class AbsListActivity<T> : AppCompatActivity() {


    private lateinit var commonListingView: CommonListingView<T>

    protected abstract val listViewModel: AbsListingModel<T>

    private var isStarted: Boolean = false

    override fun onStart() {
        super.onStart()
        if (!isStarted) {
            isStarted = true
            commonListingView = getListingView(this)
            commonListingView.setAdapter { createAdapter(it) }
            commonListingView.bindListingToListener(listViewModel.getListing())
            listViewModel
                .getListing()
                .observe(this, createListingObserver(commonListingView))
        }
    }

    protected abstract fun getListingView(context: Context): CommonListingView<T>

    protected abstract fun createListingObserver(listingView: CommonListingView<T>): ListingObserver<T>

    protected abstract fun createAdapter(data: List<T>): BaseQuickAdapter<T, *>
}