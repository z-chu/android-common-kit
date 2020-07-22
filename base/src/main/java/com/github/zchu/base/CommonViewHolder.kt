package com.github.zchu.base

import android.view.View
import androidx.annotation.IdRes
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by zchu on 16-12-1.
 * 如有其他定制化的封装可以自行添加
 */

open class CommonViewHolder(view: View) : BaseViewHolder(view) {

    open fun setSelected(@IdRes viewId: Int, selected: Boolean): CommonViewHolder {
        getView<View>(viewId).isSelected = selected
        return this
    }


}
