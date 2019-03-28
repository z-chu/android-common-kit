@file:Suppress("UNCHECKED_CAST")

package com.github.zchu.common.util

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

/**
 * Retrieve argument from Activity intent
 */
fun <T : Any> Activity.argument(key: String, defaultValue: T? = null) =
    lazy {
        intent.extras?.get(key) as? T ?: defaultValue
        ?: error("Intent Argument $key is missing")
    }


/**
 * Retrieve argument from Fragment intent
 */
fun <T : Any> Fragment.argument(key: String, defaultValue: T? = null) =
    lazy {
        arguments?.get(key) as? T ?: defaultValue ?: error("Intent Argument $key is missing")
    }

fun FragmentManager.selectFragmentDisplay(
    @IdRes containerViewId: Int, tag: String,
    block: (tag: String) -> Fragment
) {
    val beginTransaction = beginTransaction()
    for (fragment in fragments) {
        if (!fragment.isHidden) {
            beginTransaction.hide(fragment)
        }
    }
    val findFragmentByTag = findFragmentByTag(tag)
    if (findFragmentByTag == null) {
        beginTransaction.add(containerViewId, block.invoke(tag), tag)
    } else {
        beginTransaction.show(findFragmentByTag)
    }
    beginTransaction.commit()
}

inline fun <T : Fragment> FragmentManager.findOrCreateFragmentByTag(tag: String, block: () -> T): T {
    return findFragmentByTag(tag) as? T ?: block.invoke()
}

