package com.ninjahoahong.unstoppable.question

import android.os.Bundle
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

    private fun setAnswers(currentAnswers: List<String>) {
        buttonAnswer1.text = Html.fromHtml(currentAnswers[0])
        buttonAnswer2.text = Html.fromHtml(currentAnswers[1])
        buttonAnswer3.text = Html.fromHtml(currentAnswers[2])
        buttonAnswer4.text = Html.fromHtml(currentAnswers[3])

        chooseAnwser(buttonAnswer1)
        chooseAnwser(buttonAnswer2)
        chooseAnwser(buttonAnswer3)
        chooseAnwser(buttonAnswer4)
    }

    override fun onDestroyView() {
        compositeDisposable.clearIfNotDisposed()
        super.onDestroyView()
    }

    private fun chooseAnwser(answerButton: Button) {
        answerButton.setOnClickListener {
            if (currentQuestion != null) {
                if (answerButton.text == Html.fromHtml(currentQuestion.correctAnswer)) {
                    loadQuestion()
                }
            } else {
                //TODO highlight right answer and go next
            }
        }
    }
}
