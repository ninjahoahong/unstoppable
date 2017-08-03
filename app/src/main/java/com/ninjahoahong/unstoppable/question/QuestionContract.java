package com.ninjahoahong.unstoppable.question;

import com.ninjahoahong.unstoppable.BasePresenter;
import com.ninjahoahong.unstoppable.BaseView;
import com.ninjahoahong.unstoppable.data.models.ResultsItem;

import java.util.List;

public interface QuestionContract {
    interface View extends BaseView<Presenter> {

        void setLoading(boolean isLoading);

        void showError(String message);

        void showEmpty();

        void showLatestQuestions(List<ResultsItem> questions);
    }

    interface Presenter extends BasePresenter {

        void loadQuestions();
    }
}
