package com.davv1d.carrental.validate

import com.davv1d.carrental.constants.ERROR_SEPARATOR
import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.pierre.Result

class Validator<T>(override val conditions: () -> List<Condition<T>>) : ConditionValidator<T> {
    override fun valid(value: T, error: (String) -> RuntimeException): Result<T> {
        val conditionsList = conditions.invoke()
        val errors = getAllErrors(value, conditionsList)
        val errorMessage = prepareErrorMessage(errors)
        return convertToResult(value, errorMessage, error, errors.isEmpty())
    }

    private fun convertToResult(value: T, errorMessage: String, error: (String) -> RuntimeException, isSuccess: Boolean): Result<T> {
        return when {
            isSuccess -> Result.invoke(value)
            else -> Result.failure(error(errorMessage))
        }
    }

    fun prepareErrorMessage(errors: List<Condition<T>>): String {
        return errors.asSequence().map { it.errorMessageIfTrue }.joinToString(separator = ERROR_SEPARATOR)
    }

    fun <T> getAllErrors(value: T, conditions: List<Condition<T>>): List<Condition<T>> {
        return conditions.asSequence()
                .filter { conditionTest -> conditionTest.checkFunction.invoke(value) }
                .toList()
    }
}
