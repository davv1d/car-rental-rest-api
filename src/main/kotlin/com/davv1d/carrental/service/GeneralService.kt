package com.davv1d.carrental.service

import com.davv1d.carrental.pierre.Result
import org.springframework.stereotype.Component

@Component
class GeneralService {
    fun <T, U> getByValue(value: U, error: RuntimeException, function: (U) -> T?): Result<T> {
        return when(val result = function.invoke(value)) {
            null -> Result.failure(error)
            else -> Result.invoke(result)
        }
    }

    fun <T : Any> secureSave(value: T, function: (T) -> T): Result<T> {
        return try {
            Result.invoke(function(value))
        } catch (e: Exception) {
            Result.failure(e.message ?: "Save error " + value::class )
        }
    }
}