package com.map08.houseonpalm.ui.calculator

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CalculatorViewModelTest {
    /*
    private val calculation = CalculatorViewModel()

    @Test
    fun resultMustBeBiggerThanZero_ReturnsTrue() {
        val salePrice = 1000000.0
        val downPayment = 100000.0
        val interestRate = 5.0
        val amortization = 12.0
        val frequency = 3

        val result = calculation.calcMonthlyPayment(
            salePrice,
            downPayment,
            interestRate,
            amortization,
            frequency
        )
        assertTrue(result.toString().toDouble() >= 0.0)
    }
     */

    @Test
    fun valueSquared_ReturnsTrue() {
        val num = 4.0
        val numSquared = Math.pow(num, 2.0)
        val numExpected = 16.0
        assertEquals(numSquared, numExpected)
    }
}