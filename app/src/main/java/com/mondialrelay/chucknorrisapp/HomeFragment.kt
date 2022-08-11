package com.mondialrelay.chucknorrisapp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mondialrelay.chucknorrisapp.databinding.ActivityMainBinding

class HomeFragment : Fragment() {

    private val viewModel : MainViewModel by inject()
    private val binding : ActivityMainBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView
        viewModel.url.observe(this.viewLifecycleOwner) {
            Picasso.with(context).load(it).into(monImage)
        }
    }
}