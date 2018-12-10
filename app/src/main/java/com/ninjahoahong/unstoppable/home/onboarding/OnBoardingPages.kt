package com.ninjahoahong.unstoppable.home.onboarding

import com.ninjahoahong.unstoppable.R


enum class OnBoardingPages(val titleResId: Int, val layoutResId: Int) {
    RED(R.string.first, R.layout.onboarding_view_page_1),
    BLUE(R.string.second, R.layout.onboarding_view_page_2),
    GREEN(R.string.third, R.layout.onboarding_view_page_3)
}