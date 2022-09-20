package hu.bme.aut.android.viewlabor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.model.IntegerHSLColor
import hu.bme.aut.android.viewlabor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pickerGroup = PickerGroup<IntegerHSLColor>()
        pickerGroup.registerPickers(
            binding.hueSeekBar,
            binding.saturationSeekBar,
            binding.lightnessSeekBar,
            binding.alphaSeekBar
        )
    }
}