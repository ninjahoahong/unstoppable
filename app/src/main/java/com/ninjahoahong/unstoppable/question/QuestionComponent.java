package com.ninjahoahong.unstoppable.question;

import com.ninjahoahong.unstoppable.AppComponent;
import com.ninjahoahong.unstoppable.ViewScope;
import com.ninjahoahong.unstoppable.question.views.QuetionView;

import dagger.Component;

@ViewScope
@Component(dependencies = AppComponent.class, modules = { QuestionModule.class })
public interface QuestionComponent {

    void inject(QuetionView quetionView);
}
