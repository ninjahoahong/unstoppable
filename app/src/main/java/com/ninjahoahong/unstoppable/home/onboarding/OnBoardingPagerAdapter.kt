package com.ninjahoahong.unstoppable.home.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ninjahoahong.unstoppable.utils.CustomPagerAdapter

class OnBoardingPagerAdapter(val context: Context) : CustomPagerAdapter() {

    override fun getItem(container: ViewGroup, position: Int): View {
        val modelObject = OnBoardingPages.values()[position]
        val inflater = LayoutInflater.from(context)
        return inflater.inflate(modelObject.layoutResId, container, false) as ViewGroup
    }

    override fun getCount(): Int {
        return OnBoardingPages.values().size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val customPagerEnum = OnBoardingPages.values()[position]
        return context.getString(customPagerEnum.titleResId)
    }

}
