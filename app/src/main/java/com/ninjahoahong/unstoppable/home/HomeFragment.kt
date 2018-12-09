package com.ninjahoahong.unstoppable.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ninjahoahong.unstoppable.BaseFragment
import com.ninjahoahong.unstoppable.MainActivity
import com.ninjahoahong.unstoppable.R
import com.ninjahoahong.unstoppable.question.QuestionViewKey
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.sdk15.listeners.onClick

class HomeFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadGameButton.onClick { MainActivity[view.context].navigateTo(QuestionViewKey()); }
    }
}