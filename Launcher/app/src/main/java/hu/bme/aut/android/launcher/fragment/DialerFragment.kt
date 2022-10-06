package hu.bme.aut.android.launcher.fragment

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TableRow
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.core.view.iterator
import androidx.core.view.size
import androidx.fragment.app.Fragment
import hu.bme.aut.android.launcher.R
import hu.bme.aut.android.launcher.databinding.FragmentDialerBinding

class DialerFragment : Fragment() {

    private lateinit var binding: FragmentDialerBinding;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialerBinding.inflate(layoutInflater, container, false)

        for(i in 0..binding.tableLayout.childCount - 1){
            val row: TableRow = binding.tableLayout.getChildAt(i) as TableRow
            for(j in 0 .. row.childCount - 1){
                val tableItem = row.getChildAt(j)
                tableItem.setOnClickListener { item -> onButtonClick(item) }
            }
        }
        binding.btnCallBackSpace.setOnClickListener { item -> onButtonClick((item)) }
        binding.btnCall.setOnClickListener { item -> onButtonClick((item)) }

        return binding.root
    }

    var dialNumber = "";

    fun onButtonClick(item: View): Boolean{
        when (item.id) {
            R.id.btnCallBackSpace -> {
                dialNumber = dialNumber.dropLast(1)
                binding.etCall.setText(dialNumber)
            }
            R.id.btnCall -> {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + dialNumber))
                requireContext().startActivity(intent)
            }
            else -> {
                val btnItem = item as Button
                dialNumber += btnItem.text
                binding.etCall.setText(dialNumber)
            }
        }
        return true
    }
}