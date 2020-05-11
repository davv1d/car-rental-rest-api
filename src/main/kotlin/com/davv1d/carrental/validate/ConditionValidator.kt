package com.davv1d.carrental.validate

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.pierre.Result

interface ConditionValidator<T> {
    val conditions: () -> List<Condition<T>>
    fun valid(value: T, error: (String) -> RuntimeException): Result<T>
}