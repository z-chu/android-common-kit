package com.github.zchu.common.util

import android.content.Context
import androidx.annotation.ColorRes
import androidx.annotation.FontRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat

fun Context.getColorCompat(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getFontCompat(@FontRes fontRes: Int) = ResourcesCompat.getFont(this, fontRes)