package com.nbcc.quiz

data class Question(val textResID : Int, val answer : Boolean, var attempted : Boolean = false) {}