package com.davv1d.carrental.validate

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.validate.condition.ConditionGenerator

interface ConditionValidator<T> {
    val conditions: ConditionGenerator<T>
    fun dbValidate(value: T): Result<T>
    fun validate(conditions: List<Condition<T>>, error: (String) -> RuntimeException): Result<T>
}