package com.mondialrelay.chucknorrisapp.application.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mondialrelay.chucknorrisapp.databinding.FragmentHomeBinding
import org.koin.android.ext.android.get

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.jokeApi = get()
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserversListeners()
        viewModel.fireFetchButton()
    }

    private fun initObserversListeners() {
        viewModel.jokeText.observe(this.viewLifecycleOwner) {
            uiDisplayJokeText(it)
        }
        viewModel.progressBar.observe(this.viewLifecycleOwner) {
            uiAnimProgressBar(it)
        }
        binding.button.setOnClickListener {
            viewModel.fireFetchButton()
        }
    }

    // endregion

    // region -------- UI

    private fun uiDisplayJokeText(text: String) {
        uiAnimProgressBar(false)
        binding.textView2.text = text
        binding.textView2.visibility = View.VISIBLE
    }

    private fun uiAnimProgressBar(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.isIndeterminate = true
            binding.button.isEnabled = false
            binding.textView2.visibility = View.GONE
            binding.textView2.text = ""
        } else {
            binding.progressBar.visibility = View.GONE
            binding.button.isEnabled = true
            binding.textView2.visibility = View.VISIBLE
        }
    }

    // endregion

}