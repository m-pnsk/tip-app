package com.example.tiptime

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.currencySign.text = Currency.getInstance(Locale.getDefault()).symbol
        binding.optionCustomPercent.setOnClickListener { activateSeekBar() }
        binding.calculateButton.setOnClickListener { calculateTip() }
        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seek: SeekBar,
                progress: Int, fromUser: Boolean
            ) {
                binding.optionCustomPercent.text = "$progress%"
            }

            override fun onStartTrackingTouch(seek: SeekBar){} // do nothing

            override fun onStopTrackingTouch(seek: SeekBar) {}// do nothing

        })
    }

    private fun activateSeekBar() {
        binding.seekBar.visibility = View.VISIBLE
    }

    private fun calculateTip() {
        val stringServiceCost = binding.costOfService.text.toString()
        val serviceCost = if (stringServiceCost.isNotEmpty()) stringServiceCost.toDouble() else 0.0
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.2
            R.id.option_fifteen_percent -> 0.15
            R.id.option_custom_percent -> binding.seekBar.progress / 100.0
            else -> 0.10
        }
        var tip = tipPercentage * serviceCost

        if (binding.roundUpSwitch.isChecked) {
            tip = ceil(tip)
        }
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}