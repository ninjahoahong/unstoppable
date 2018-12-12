package com.ninjahoahong.unstoppable.question

import android.os.Bundle
import android.os.Handler
import android.support.v4.content.ContextCompat
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ninjahoahong.unstoppable.AndroidApp
import com.ninjahoahong.unstoppable.BaseFragment
import com.ninjahoahong.unstoppable.R
import com.ninjahoahong.unstoppable.api.AppService
import com.ninjahoahong.unstoppable.data.responses.ResultsItem
import com.ninjahoahong.unstoppable.utils.SchedulerProvider
import com.ninjahoahong.unstoppable.utils.clearIfNotDisposed
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.fragment_question.*
import kotlinx.android.synthetic.main.question_view.button_answer1
import kotlinx.android.synthetic.main.view_loading.*
import javax.inject.Inject

class QuestionViewFragment : BaseFragment() {

    private lateinit var currentQuestion: ResultsItem

    @Inject
    lateinit var appService: AppService

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @Inject
    lateinit var schedulerProvider: SchedulerProvider

    private var indexOfContentView: Int = 0
    private var indexOfLoadingView: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AndroidApp.appComponent.inject(this)
        indexOfContentView = viewAnimator.indexOfChild(questionContainer)
        indexOfLoadingView = viewAnimator.indexOfChild(loadingView)
        loadQuestion()
    }

    private fun loadQuestion() {
        viewAnimator.displayedChild = indexOfLoadingView
        compositeDisposable += appService
            .getQuestion()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .doFinally { viewAnimator.displayedChild = indexOfContentView }
            .subscribeBy(
                onNext = {
                    println("onNext!")
                    currentQuestion = it.results[0]
                    tvQuestion.text = Html.fromHtml(currentQuestion.question)
                    val currentAnswers = ArrayList<String>(4)
                    currentAnswers.addAll(currentQuestion.inCorrectAnswers)
                    currentAnswers.add(currentQuestion.correctAnswer)
                    currentAnswers.shuffle()
                    resetAllButtonStyles()
                    setAnswers(currentAnswers)
                },
                onError = {
                    it.printStackTrace()
                },
                onComplete = {
                    println("Done!")
                }
            )
    }

    private fun resetAllButtonStyles() {
        val upperLimit = questionContainer.childCount - 1
        for (i in 0..upperLimit) {
            val child = questionContainer.getChildAt(i)
            if (child is Button) {
                child.isClickable = true
                child.isActivated = true
                child.isSelected = false
                child.setTextColor(ContextCompat.getColor(activity!!, android.R.color.white))
            }
        }
    }

    private fun setAnswers(currentAnswers: List<String>) {
        buttonAnswer1.text = Html.fromHtml(currentAnswers[0])
        buttonAnswer2.text = Html.fromHtml(currentAnswers[1])
        buttonAnswer3.text = Html.fromHtml(currentAnswers[2])
        buttonAnswer4.text = Html.fromHtml(currentAnswers[3])

        val upperLimit = questionContainer.childCount - 1
        for (i in 0..upperLimit) {
            val child = questionContainer.getChildAt(i)
            if (child is Button) {
                chooseAnwser(child)
            }
        }
    }

    override fun onDestroyView() {
        compositeDisposable.clearIfNotDisposed()
        super.onDestroyView()
    }

    private fun chooseAnwser(answerButton: Button) {
        answerButton.setOnClickListener {
            disableAllButtons()
            if (currentQuestion != null) {
                if (answerButton.text == Html.fromHtml(currentQuestion.correctAnswer)) {
                    loadQuestion()
                } else {
                    answerButton.setTextColor(ContextCompat.getColor(
                        activity!!, android.R.color.holo_red_dark))
                    highlightCorrectAnswer()
                    Handler().postDelayed({
                        loadQuestion()
                    }, 1000)
                }
            }
        }
    }

    private fun disableAllButtons() {
        val upperLimit = questionContainer.childCount - 1
        for (i in 0..upperLimit) {
            val child = questionContainer.getChildAt(i)
            if (child is Button) {
                child.isClickable = false
            }
        }
    }

    private fun highlightCorrectAnswer() {
        val upperLimit = questionContainer.childCount - 1
        for (i in 0..upperLimit) {
            val child = questionContainer.getChildAt(i)
            if (child is Button) {
                if (child.text == Html.fromHtml(currentQuestion.correctAnswer)) {
                    child.isSelected = true
                }
            }
        }
    }
}
