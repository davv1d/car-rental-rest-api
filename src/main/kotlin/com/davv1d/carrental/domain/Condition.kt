package com.davv1d.carrental.domain

class Condition<T>(val errorMessageIfTrue: String, val checkFunction: (T) -> Boolean)