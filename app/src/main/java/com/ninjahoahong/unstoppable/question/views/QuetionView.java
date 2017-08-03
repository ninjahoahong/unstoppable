package com.ninjahoahong.unstoppable.question.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.ninjahoahong.unstoppable.AndroidApp;
import com.ninjahoahong.unstoppable.R;
import com.ninjahoahong.unstoppable.data.models.ResultsItem;
import com.ninjahoahong.unstoppable.question.DaggerQuestionComponent;
import com.ninjahoahong.unstoppable.question.QuestionContract;
import com.ninjahoahong.unstoppable.question.QuestionModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static dagger.internal.Preconditions.checkNotNull;

public class QuetionView extends FrameLayout implements QuestionContract.View {

    @BindView(R.id.view_error)
    TextView errorView;

    @BindView(R.id.view_empty)
    TextView emptyView;

    @BindView(R.id.view_loading)
    ProgressBar loadingView;

    @BindView(R.id.viewanimator_content)
    ViewAnimator viewAnimator;

    @BindView(R.id.layout_question_container)
    LinearLayout questionContainerLayout;

    @BindView(R.id.textview_question)
    TextView questionTextView;

    @BindView(R.id.button_answer1)
    Button answerButton1;

    @BindView(R.id.button_answer2)
    Button answerButton2;

    @BindView(R.id.button_answer3)
    Button answerButton3;

    @BindView(R.id.button_answer4)
    Button answerButton4;

    @Inject
    QuestionContract.Presenter questionPresenter;

    private ResultsItem currentQuestion;

    public QuetionView(Context context) {
        super(context);
    }

    public QuetionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    public void init() {
        DaggerQuestionComponent.builder()
                .appComponent(AndroidApp.getAppComponent())
                .questionModule(new QuestionModule(this))
                .build()
                .inject(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        questionPresenter.register();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        questionPresenter.unregister();
    }

    @Override
    public void setLoading(boolean isLoading) {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(loadingView));
    }

    @Override
    public void showError(String message) {
        errorView.setText(message);
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(errorView));
    }

    @Override
    public void showEmpty() {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(emptyView));
    }

    @Override
    public void showLatestQuestions(List<ResultsItem> questions) {
        viewAnimator.setDisplayedChild(viewAnimator.indexOfChild(questionContainerLayout));
        currentQuestion = questions.get(0);
        questionTextView.setText(Html.fromHtml(currentQuestion.question()));
        List<String> currentAnswers = new ArrayList<>(4);
        currentAnswers.addAll(currentQuestion.incorrectAnswers());
        currentAnswers.add(currentQuestion.correctAnswer());
        Collections.shuffle(currentAnswers);
        setAnswers(currentAnswers);
    }

    private void setAnswers(List<String> currentAnswers) {
        answerButton1.setText(Html.fromHtml(currentAnswers.get(0)));
        answerButton2.setText(Html.fromHtml(currentAnswers.get(1)));
        answerButton3.setText(Html.fromHtml(currentAnswers.get(2)));
        answerButton4.setText(Html.fromHtml(currentAnswers.get(3)));
    }

    @OnClick(R.id.button_answer1)
    void chooseAnswer1() {
        chooseAnwser(answerButton1);
    }

    @OnClick(R.id.button_answer2)
    void chooseAnswer2() {
        chooseAnwser(answerButton2);
    }

    @OnClick(R.id.button_answer3)
    void chooseAnswer3() {
        chooseAnwser(answerButton3);
    }

    @OnClick(R.id.button_answer4)
    void chooseAnswer4() {
        chooseAnwser(answerButton4);
    }

    private void chooseAnwser(Button answerButton) {
        if (currentQuestion != null) {
            if (answerButton.getText().equals(Html.fromHtml(currentQuestion.correctAnswer()))) {
                questionPresenter.loadQuestions();
            }
        } else {

        }
    }

    @Override
    public void presenter(@NonNull QuestionContract.Presenter presenter) {
        questionPresenter = checkNotNull(presenter);
    }


}