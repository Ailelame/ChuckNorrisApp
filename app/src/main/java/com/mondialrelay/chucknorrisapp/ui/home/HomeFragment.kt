package com.mondialrelay.chucknorrisapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.mondialrelay.chucknorrisapp.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val viewModel : HomeViewModel by inject()
    private lateinit var binding : FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (viewModel.url as MutableLiveData).observe(this.viewLifecycleOwner) {
            binding.textView2.text = it

        }
        viewModel.test()

     //   (viewModel.url as MutableLiveData).postValue("odsfnl")
    }


}