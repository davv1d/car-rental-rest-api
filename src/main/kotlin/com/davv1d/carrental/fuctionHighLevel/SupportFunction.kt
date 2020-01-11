package com.davv1d.carrental.fuctionHighLevel

import org.slf4j.Logger

fun writeAndThrowError(logger: Logger, error: RuntimeException) {
    logger.error(error.message)
    throw error
}
//
//fun <T> mapToSuccessList(list: List<Result<T>>): List<T> {
//    return list.asSequence()
//            .filter { result -> !result.isEmpty() && !result.isFailure() }
//            .map { result -> result.getOrElse { throw IllegalStateException("Element failure") } }
//            .toList()
//}
//
//fun <T> mapToFailureErrorList(list: List<Result<T>>): List<String> {
//    return list.asSequence()
//            .filter { result -> result.isEmpty() || result.isFailure() }
//            .map { result -> result.message() }
//            .toList()
//}
//
//fun <T, U> Collection<Result<T>>.mapToList(filter: (Result<T>) -> Boolean, map: (Result<T>) -> U): List<U> {
//    return this.asSequence()
//            .filter(filter)
//            .map(map)
//            .toList()
//}
//
//fun <T> Collection<Result<T>>.mapToResultList(): Result<List<T>> {
//    val errorList = this.mapToList(
//            { r -> r.isEmpty() || r.isFailure() },
//            { r -> r.message() })
//    return if (errorList.isEmpty()) {
//        Result.invoke(this.mapToList(
//                { r -> !r.isEmpty() && !r.isFailure() },
//                { r -> r.getOrElse { throw IllegalStateException("Element failure") }}))
//        } else {
//            Result.failure(NotFoundElementException(errorList.joinToString(ERROR_SEPARATOR)))
//        }
//    }
//
//fun <T> mapToResult(list: List<Result<T>>): Result<List<T>> {
//    val errorList = mapToFailureErrorList(list)
//    return if (errorList.isEmpty()) {
//        Result.invoke(mapToSuccessList(list))
//    } else {
//        Result.failure(NotFoundElementException(errorList.joinToString(ERROR_SEPARATOR)))
//    }
//}