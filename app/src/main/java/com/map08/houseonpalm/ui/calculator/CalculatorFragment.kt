package com.map08.houseonpalm.ui.calculator

import android.annotation.SuppressLint
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.map08.houseonpalm.databinding.FragmentCalculatorBinding

class CalculatorFragment(): Fragment() {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    
    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val calculatorViewModel =
            ViewModelProvider(this).get(CalculatorViewModel::class.java)

        _binding = FragmentCalculatorBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // variables to calculate from editText
        val salePrice: TextView = binding.editTextSalePrice
        val downPayment: TextView = binding.editTextDownPayment
        val interestRate: TextView = binding.editTextInterestRate

        var salePriceNum : Double
        var downPaymentNum : Double
        var interestRateNum : Double

        /*
        Tried to change the display while user was typing but failed
        salePrice.text = "$ " + DecimalFormat("#,###.##").format(salePriceNum)
        downPayment.text = "$ " + DecimalFormat("#,###.##").format(downPaymentNum)
        interestRate.text = "% " + DecimalFormat("#.##").format(interestRateNum)
         */

        // progress circle bar
        //val progress: ProgressBar = binding.progressBar

        // seek bar amortization
        val amortization: SeekBar = binding.seekBarAmortization
        val amortizationDisplay: TextView = binding.textAmortizationDisplay
        var startPointAmort = 0
        var endPointAmort = 0
        var amort = 25

        // seek bar frequency
        val frequency: SeekBar = binding.seekBarFrequency
        val frequencyDisplay: TextView = binding.textFrequencyDisplay
        var startPointFreq = 0
        var endPointFreq = 0
        var freq = 12

        // Monthly Display
        val monthly: TextView = binding.textMonthlyPaymentDisplay

        // Button
        val buttonCalculate: Button = binding.buttonCalculate
        val buttonRestart: Button = binding.buttonRestart

        amortization.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    amort = progress
                    amortizationDisplay.text = "$amort years"
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    startPointAmort = seekBar.progress
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    endPointAmort = seekBar.progress
                }
            }
        )

        frequency.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar,
                progress: Int,
                fromUser: Boolean
            ) {
                if (progress == 1) {
                    frequencyDisplay.text = "Weekly"
                    freq = 52
                } else if (progress == 2) {
                    frequencyDisplay.text = "Bi-Weekly"
                    freq = 24
                } else if (progress == 3) {
                    frequencyDisplay.text = "Monthly"
                    freq = 12
                } else if (progress == 4) {
                    frequencyDisplay.text = "Every 2 months"
                    freq = 6
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                startPointFreq = seekBar.progress
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                endPointFreq = seekBar.progress
            }

        })

        buttonCalculate.setOnClickListener {
            if (salePrice.text.isNullOrBlank()) {
                salePrice.text = "0.0"
            }

            if (downPayment.text.isNullOrBlank()) {
                downPayment.text = "0.0"
            }

            if (interestRate.text.isNullOrBlank()) {
                interestRate.text = "0.0"
            }

            salePriceNum = salePrice.text.toString().toDouble()
            downPaymentNum = downPayment.text.toString().toDouble()
            interestRateNum = interestRate.text.toString().toDouble()

            if (salePriceNum <= 0.0 || downPaymentNum <= 0.0 || interestRateNum <= 0.0) {
                val message: CharSequence = "No input value can be zero or negative!"
                Toast.makeText(root.context, message, Toast.LENGTH_SHORT).show()
            } else {
                val monthlyPay = calculatorViewModel.calcMonthlyPayment(
                    salePriceNum,
                    downPaymentNum,
                    interestRateNum / 100,
                    amort.toString().toDouble(),
                    freq
                ).toString()
                monthly.text = "$ " + DecimalFormat("#,###.##").format(monthlyPay.toDouble()).toString()
            }
            salePrice.text = ""
            downPayment.text = ""
            interestRate.text = ""
            amortization.progress = 25
            frequency.progress = 3
        }

        buttonRestart.setOnClickListener {
            salePrice.text = ""
            downPayment.text = ""
            interestRate.text = ""
            amortization.progress = 25
            frequency.progress = 3
            monthly.text = "$ 0"
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}