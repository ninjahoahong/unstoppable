package com.ninjahoahong.unstoppable.home

import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ninjahoahong.unstoppable.BaseFragment
import com.ninjahoahong.unstoppable.MainActivity
import com.ninjahoahong.unstoppable.R
import com.ninjahoahong.unstoppable.home.onboarding.OnBoardingPagerAdapter
import com.ninjahoahong.unstoppable.question.QuestionViewKey
import com.ninjahoahong.unstoppable.utils.Settings
import kotlinx.android.synthetic.main.fragment_home.continueButton
import kotlinx.android.synthetic.main.fragment_home.introText
import kotlinx.android.synthetic.main.fragment_home.loadGameButton
import kotlinx.android.synthetic.main.fragment_home.onBoardingView
import kotlinx.android.synthetic.main.fragment_home.onBoardingViewsContainer
import kotlinx.android.synthetic.main.fragment_home.viewPagerIndicator
import org.jetbrains.anko.sdk15.listeners.onClick

class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showOnboardingView(Settings.isNot(view.context, Settings.FIRST_TIME_PLAY))
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            introText.text = Html.fromHtml(
                getString(R.string.intro_text), Html.FROM_HTML_MODE_LEGACY)
        } else {
            @Suppress("DEPRECATION")
            introText.text = Html.fromHtml(getString(R.string.intro_text))
        }
        introText.movementMethod = LinkMovementMethod.getInstance()
        loadGameButton.onClick { MainActivity[view.context].navigateTo(QuestionViewKey()); }
    }

    private fun showOnboardingView(shouldShowed: Boolean) {
        onBoardingView.adapter = OnBoardingPagerAdapter(activity as MainActivity)
        viewPagerIndicator.setupWithViewPager(onBoardingView)
        continueButton.setOnClickListener { inactivateOnboarding() }

        return if (shouldShowed) {
            (activity as MainActivity).supportActionBar!!.hide()
            onBoardingViewsContainer.visibility = View.VISIBLE
        } else {
            inactivateOnboarding()
        }
    }

    private fun inactivateOnboarding() {
        (activity as MainActivity).supportActionBar!!.show()
        onBoardingViewsContainer.visibility = View.GONE
        Settings[activity as MainActivity, Settings.FIRST_TIME_PLAY] = true
    }
}