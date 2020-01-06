package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition

interface ConditionGenerator<T> {
    fun get(value: T): List<Condition<T>>
}