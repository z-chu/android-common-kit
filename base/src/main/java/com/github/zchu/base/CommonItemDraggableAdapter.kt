package com.github.zchu.base

import android.view.View
import android.view.ViewGroup

import com.chad.library.adapter.base.BaseItemDraggableAdapter

/**
 * author : zchu
 * date   : 2017/9/15
 * desc   :
 */

abstract class CommonItemDraggableAdapter<T> : BaseItemDraggableAdapter<T, CommonViewHolder> {

    constructor(layoutResId: Int, data: List<T>) : super(layoutResId, data) {}

    constructor(data: List<T>) : super(data) {}

    override fun createBaseViewHolder(parent: ViewGroup, layoutResId: Int): CommonViewHolder {
        return this.createBaseViewHolder(this.getItemView(layoutResId, parent))
    }

    override fun createBaseViewHolder(view: View): CommonViewHolder {
        return CommonViewHolder(view)
    }
}
