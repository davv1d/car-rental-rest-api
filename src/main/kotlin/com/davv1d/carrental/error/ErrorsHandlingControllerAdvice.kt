package com.davv1d.carrental.error

import com.davv1d.carrental.constants.ERROR_SEPARATOR
import com.davv1d.carrental.constants.MUST_NOT_BE_NULL
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ErrorHand {
    @ExceptionHandler(MissingKotlinParameterException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun nullValueInParameters(e: MissingKotlinParameterException): ValidationErrorResponse {
        return ValidationErrorResponse(e.path
                .map { r -> Violation(r.fieldName + MUST_NOT_BE_NULL) }
                .toList())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun onMethodArgumentNotValidException(e: MethodArgumentNotValidException): ValidationErrorResponse {
        val filedErrors = e.bindingResult.fieldErrors
        val violations = filedErrors.asSequence()
                .map { fieldError -> Violation(fieldError.defaultMessage) }
                .toList()
        return ValidationErrorResponse(violations)
    }

    @ExceptionHandler(NotFoundElementException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun notFoundElementInDatabase(e: NotFoundElementException): ValidationErrorResponse {
        val violations = listOf(Violation(e.message))
        return ValidationErrorResponse(violations)
    }

    @ExceptionHandler(ElementExistsException::class)
    @ResponseStatus(BAD_REQUEST)
    @ResponseBody
    fun elementExistInDatabase(e: ElementExistsException): ValidationErrorResponse {
        val message = e.message ?: "none"
        val violations = message.split(ERROR_SEPARATOR)
                .toTypedArray()
                .asSequence()
                .map(::Violation)
                .toList()
        return ValidationErrorResponse(violations)
    }
}