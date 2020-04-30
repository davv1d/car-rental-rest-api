package com.davv1d.carrental.validate

import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.validate.condition.ConditionGenerator

interface ConditionValidator<T> {
    val conditions: ConditionGenerator<T>
    fun valid(value: T, error: (String) -> RuntimeException): Result<T>
}