package com.davv1d.carrental.validate.condition

import com.davv1d.carrental.domain.Condition
import com.davv1d.carrental.domain.Rental
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class RentalSaveDateConditions : ConditionGenerator<Rental> {
    override fun get(): List<Condition<Rental>> {
        val condition1 = Condition<Rental>("DATE OF RENT IS BEFORE CURRENT DATE + 1H") { rental -> with(rental) { dateOfRent.isBefore(LocalDateTime.now().plusHours(1)) } }
        val condition2 = Condition<Rental>("DATE OF RENT + 1H IS AFTER DATE OF RETURN") { rental -> with(rental) { dateOfRent.isAfter(dateOfReturn) } }
        return listOf(condition1, condition2)
    }
}