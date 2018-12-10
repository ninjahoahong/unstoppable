package com.ninjahoahong.unstoppable.utils

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

abstract class CustomPagerAdapter : PagerAdapter() {

    final override fun isViewFromObject(view: View, obj: Any): Boolean = view === obj

    abstract fun getItem(container: ViewGroup, position: Int): View

    @Suppress("NAME_SHADOWING")
    final override fun instantiateItem(container: ViewGroup, position: Int): Any
        = position.takeIf { position in 0 until count }
        ?.let { position -> getItem(container, position) }
        ?.also { container.addView(it) }
        ?: throw IllegalArgumentException("Position $position not handled!")

    final override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }
}