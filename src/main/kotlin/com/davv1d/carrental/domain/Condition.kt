package com.davv1d.carrental.domain

class Condition<T>(val valueToCheck: T, val errorMessageIfTrue: String, val checkFunction: (T) -> Boolean) {
    fun invoke(): Boolean = checkFunction.invoke(valueToCheck)
}