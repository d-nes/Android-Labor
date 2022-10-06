package hu.bme.aut.android.launcher.fragment

import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hu.bme.aut.android.launcher.R
import hu.bme.aut.android.launcher.databinding.FragmentApplicationsBinding
import hu.bme.aut.android.launcher.databinding.FragmentBrowserBinding

class BrowserFragment : Fragment() {
    private lateinit var binding: FragmentBrowserBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBrowserBinding.inflate(layoutInflater, container, false)
        binding.button.setOnClickListener {onSeachClicked()}
        return binding.root
    }

    fun onSeachClicked() {
        val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
            putExtra(SearchManager.QUERY, binding.editText.text.toString())
        }
        startActivity(intent)
    }
}