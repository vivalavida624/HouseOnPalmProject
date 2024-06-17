package com.map08.houseonpalm.ui.calculator

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.annotations.TestOnly
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorViewModel: ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Calculator Fragment"
    }
    val text: LiveData<String> = _text

    fun calcMonthlyPayment(
        salePrice: Double,
        downPayment: Double,
        interestRate: Double,
        amortization: Double,
        frequency: Int
    ): BigDecimal? {

        val monthlyPay = ((salePrice - downPayment) * (interestRate / frequency)) / (1 - (1 / (Math.pow(1 + (interestRate / frequency), (frequency * amortization)))))
        return BigDecimal(monthlyPay).setScale(2, RoundingMode.CEILING)

    }

}