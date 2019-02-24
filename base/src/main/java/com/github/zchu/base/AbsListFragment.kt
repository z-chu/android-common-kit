package com.github.zchu.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.github.zchu.listing.AbsListingModel
import com.github.zchu.listing.ListingObserver

abstract class AbsListFragment<T> : Fragment() {

    private lateinit var commonListingView: CommonListingView<T>

    protected abstract val listViewModel: AbsListingModel<T>


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commonListingView = getListingView(requireContext())
        commonListingView.setAdapter { createAdapter(it) }
        commonListingView.setOnLoadMoreListener {
            listViewModel.getListing().value?.loadMore?.invoke()
        }
        commonListingView.setOnRefreshListener {
            listViewModel.getListing().value?.refresh?.invoke()
        }
        commonListingView.setOnRetryListener {
            listViewModel.getListing().value?.retry?.invoke()
        }
        listViewModel
            .getListing()
            .observe(this, createListingObserver(commonListingView))
    }

    protected abstract fun getListingView(context: Context): CommonListingView<T>

    protected abstract fun createListingObserver(listingView: CommonListingView<T>): ListingObserver<T>

    protected abstract fun createAdapter(data: List<T>): BaseQuickAdapter<T, *>
}