package com.ninjahoahong.unstoppable.question;

import android.support.annotation.NonNull;

import com.ninjahoahong.unstoppable.data.models.Question;
import com.ninjahoahong.unstoppable.data.models.ResultsItem;
import com.ninjahoahong.unstoppable.data.source.QuestionRepository;
import com.ninjahoahong.unstoppable.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class QuestionPresenter implements QuestionContract.Presenter {

    private final QuestionContract.View questionView;
    private final CompositeDisposable compositeDisposable;
    private final QuestionRepository questionRepository;
    private final BaseSchedulerProvider schedulerProvider;

    @Inject
    public QuestionPresenter(@NonNull QuestionRepository questionRepository,
                             @NonNull QuestionContract.View questionView,
                             @NonNull BaseSchedulerProvider schedulerProvider) {
        this.questionRepository = questionRepository;
        this.questionView = questionView;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
    }

    @Inject
    void attachPresenter() {
        questionView.presenter(this);
    }

    @Override
    public void loadQuestions() {
        compositeDisposable.clear();
        questionView.setLoading(true);
        Disposable disposable = questionRepository.getQuestions()
                .map(Question::results)
                .subscribeOn(schedulerProvider.background())
                .observeOn(schedulerProvider.main())
                .subscribe(
                        this::processQuestions,
                        this::handleError
                );
        compositeDisposable.add(disposable);
    }

    private void handleError(Throwable throwable) {
        questionView.setLoading(false);
        questionView.showError(throwable.getLocalizedMessage());
    }

    private void processQuestions(List<ResultsItem> questions) {
        questionView.setLoading(false);
        if (questions.isEmpty()) {
            questionView.showEmpty();
        } else {
            questionView.showLatestQuestions(questions);
        }
    }

    @Override
    public void register() {
        loadQuestions();
    }

    @Override
    public void unregister() {
        compositeDisposable.clear();
    }

}
