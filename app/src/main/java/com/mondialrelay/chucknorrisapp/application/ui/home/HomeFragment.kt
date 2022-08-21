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
    private lateinit var viewBinding: FragmentHomeBinding

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.jokeApi = get()
        viewBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserversListeners()
        viewModel.fireFetchButton()
    }

    // endregion

    // region -------- UI

    private fun initObserversListeners() {
        viewModel.jokeText.observe(this.viewLifecycleOwner) {
            uiDisplayJokeText(it)
        }
        viewModel.progressBar.observe(this.viewLifecycleOwner) {
            uiAnimProgressBar(it)
        }
        viewBinding.button.setOnClickListener {
            viewModel.fireFetchButton()
        }
    }

    private fun uiDisplayJokeText(text: String) {
        uiAnimProgressBar(false)
        viewBinding.textView2.text = text
        viewBinding.textView2.visibility = View.VISIBLE
    }

    private fun uiAnimProgressBar(state: Boolean) {
        if (state) {
            viewBinding.progressBar.visibility = View.VISIBLE
            viewBinding.progressBar.isIndeterminate = true
            viewBinding.button.isEnabled = false
            viewBinding.textView2.visibility = View.GONE
            viewBinding.textView2.text = ""
        } else {
            viewBinding.progressBar.visibility = View.GONE
            viewBinding.button.isEnabled = true
            viewBinding.textView2.visibility = View.VISIBLE
        }
    }

    // endregion

}