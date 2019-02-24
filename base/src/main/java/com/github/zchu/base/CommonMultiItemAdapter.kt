package com.github.zchu.base

import android.view.View
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Created by zchu on 16-12-1.
 */

abstract class CommonMultiItemAdapter<T : MultiItemEntity>(data: List<T>?) :
    BaseMultiItemQuickAdapter<T, CommonViewHolder>(data) {

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): CommonViewHolder {
        return this.createBaseViewHolder(this.getItemView(layoutResId, parent))
    }

    override fun createBaseViewHolder(view: View): CommonViewHolder {
        return CommonViewHolder(view)
    }


}
