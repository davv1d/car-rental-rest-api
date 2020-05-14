package com.davv1d.carrental.domain

class Condition<T>(val errorMessageIfFalse: String, val checkFunction: (T) -> Boolean)