package com.davv1d.carrental.validate

import com.davv1d.carrental.constants.ERROR_SEPARATOR
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.error.ElementExistsException
import com.davv1d.carrental.fuctionHighLevel.joinToString
import com.davv1d.carrental.pierre.Result
import com.davv1d.carrental.validate.condition.ConditionGenerator

class Validator<T>(override val conditions: ConditionGenerator<T>) : ConditionValidator<T> {

    fun <T> getErrorsMessages(conditions: List<Condition<T>>): List<String> {
        return conditions.asSequence()
                .filter(Condition<T>::invoke)
                .map(Condition<T>::errorMessageIfTrue)
                .toList()
    }

    fun <T> checkDoErrorsExist(value: T, errorsList: List<String>, error: (String) -> RuntimeException): Result<T> {
        return when {
            errorsList.isEmpty() -> { Result.invoke(value) }
            else -> {
                val message = errorsList.joinToString(separator = ERROR_SEPARATOR)
                Result.failure(error(message))
            }
        }
    }

    override fun dbValidate(value: T): Result<T> {
        val conditions = conditions.get(value)
        return validate(conditions, ::ElementExistsException)
    }

    override fun validate(conditions: List<Condition<T>>, error: (String) -> RuntimeException): Result<T> {
        val errorsMessages = getErrorsMessages(conditions)
        return checkDoErrorsExist(conditions[0].valueToCheck, errorsMessages, error)
    }
}