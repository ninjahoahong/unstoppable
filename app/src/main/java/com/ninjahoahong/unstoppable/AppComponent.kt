package com.ninjahoahong.unstoppable

import com.ninjahoahong.unstoppable.question.QuestionViewFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    (AppModule::class)
])
interface AppComponent {
    fun inject(questionViewFragment: QuestionViewFragment)
}