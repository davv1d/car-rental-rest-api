package com.davv1d.carrental.service

import com.davv1d.carrental.pierre.Result
import org.springframework.stereotype.Component
import java.util.*

@Component
class GeneralService {
    fun <T, U> getByValue(value: U, error: RuntimeException, function: (U) -> Optional<T>): Result<T> {
        val result = function.invoke(value)
        return when(result.isPresent) {
            false -> Result.failure(error)
            else -> Result.invoke(result.get())
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