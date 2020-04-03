package com.nbcc.assignment1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nbcc.quiz.Question

class SharedViewModel(application: Application) : AndroidViewModel(application) {
    private var answered = 0
    private var correct = 0
    private var questionIndex = 0
    private var questionBank = resetQuestionBank()

    private val _scoreString = MutableLiveData("0/0")
    val scoreString: LiveData<String>
        get() = _scoreString

    private val _enableButtons = MutableLiveData(true)
    val enableButtons: LiveData<Boolean>
        get() = _enableButtons

    private val _question = MutableLiveData(application.resources.getString(questionBank[questionIndex].textResID))
    val question: LiveData<String>
        get() = _question

    var answer: Boolean
        get() = questionBank[questionIndex].answer
        private set(value) {}

    var answerText: String
        get() = when (answer) {
            true -> "True"
            false -> "False"
        }
        private set(value) {}

    private val _navigateToCheat = MutableLiveData(false)
    val navigateToCheat: LiveData<Boolean>
        get() = _navigateToCheat

    private val _navigateToGame = MutableLiveData(false)
    val navigateToGame: LiveData<Boolean>
        get() = _navigateToGame

    private val _showAnswer = MutableLiveData(false)
    val showAnswer: LiveData<Boolean>
        get() = _showAnswer

    fun doneNavigating() {
        _navigateToCheat.value = false
        _navigateToGame.value = false
    }

    fun onCheat() {
        _navigateToCheat.value = true
    }

    fun didCheat() {
        questionBank[questionIndex].attempted = true
        _enableButtons.value = false
        _showAnswer.value = true
    }

    init {
        updateQuestion()
    }

    private fun updateQuestion() {
        _question.value = getApplication<Application>().resources.getString(questionBank[questionIndex].textResID)
        _enableButtons.value = !questionBank[questionIndex].attempted
    }

    fun tryAnswer(answer: Boolean) {
        if (answer == questionBank[questionIndex].answer) {
            correct++
        }

        answered++

        _scoreString.value = "${correct}/${answered}"

        questionBank[questionIndex].attempted = true
        _enableButtons.value = false
    }

    fun onNext() {
        questionIndex = (questionIndex + 1) % 20
        updateQuestion()
    }

    fun onBack() {
        questionIndex = if (questionIndex != 0) { questionIndex - 1 } else { 19 }
        updateQuestion()
    }

    fun onCancel() {
        _navigateToGame.value = true
    }

    fun hideAnswer() {
        _showAnswer.value = false
    }

    fun newGame() {
        questionBank = resetQuestionBank()
        questionIndex = 0
        answered = 0
        correct = 0
        _scoreString.value = "${correct}/${answered}"
        updateQuestion()
    }

    private fun resetQuestionBank() : List<Question> {
        return listOf(
            Question(R.string.question_1, false),
            Question(R.string.question_2, true),
            Question(R.string.question_3, true),
            Question(R.string.question_4, false),
            Question(R.string.question_5, false),
            Question(R.string.question_6, true),
            Question(R.string.question_7, false),
            Question(R.string.question_8, true),
            Question(R.string.question_9, false),
            Question(R.string.question_10, false),
            Question(R.string.question_11, false),
            Question(R.string.question_12, true),
            Question(R.string.question_13, false),
            Question(R.string.question_14, true),
            Question(R.string.question_15, false),
            Question(R.string.question_16, false),
            Question(R.string.question_17, true),
            Question(R.string.question_18, false),
            Question(R.string.question_19, false),
            Question(R.string.question_20, true)
        )
    }
}