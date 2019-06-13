@file:Suppress("UNCHECKED_CAST")

package com.github.zchu.common.util

import android.app.Activity
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager


fun <T : Any> Activity.bindExtra(key: String, defaultValue: T) =
    lazy {
        intent.extras?.get(key) as? T ?: defaultValue
    }

fun <T : Any> Activity.bindExtra(key: String) =
    lazy {
        intent.extras?.get(key) as? T
    }

fun <T : Any> Activity.extra(key: String, defaultValue: T) =
    intent.extras?.get(key) as? T ?: defaultValue

fun <T : Any> Activity.extra(key: String) =
    intent.extras?.get(key) as? T

fun <T : Any> Fragment.bindArgument(key: String, defaultValue: T) =
    lazy {
        arguments?.get(key) as? T ?: defaultValue
    }

fun <T : Any> Fragment.bindArgument(key: String) =
    lazy {
        arguments?.get(key) as? T
    }

fun <T : Any> Fragment.argument(key: String, defaultValue: T) =
    arguments?.get(key) as? T ?: defaultValue

fun <T : Any> Fragment.argument(key: String) =
    arguments?.get(key) as? T

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

