package com.mondialrelay.chucknorrisapp.application.ui.home

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mondialrelay.chucknorrisapp.databinding.FragmentSwipeBinding
import org.koin.android.ext.android.get

class SwipeFragment : Fragment() {

    private lateinit var viewModel: SwipeViewModel
    private lateinit var viewBinding: FragmentSwipeBinding

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SwipeViewModel::class.java]
        viewModel.jokeApi = get()

        viewBinding = FragmentSwipeBinding.inflate(layoutInflater, container, false)
        viewBinding.list.adapter = arrayAdapter

        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserversListeners()
    }

    // endregion

    // region -------- UI

    private fun initObserversListeners() {
        viewModel.jokeText.observe(this.viewLifecycleOwner) {
            uiDisplayJokeText(it)
        }
        viewBinding.swiperefresh.setOnRefreshListener {
            viewModel.fireRefresh()
        }
    }

    private fun uiDisplayJokeText(text: String) {
        arrayList.add(0, text) // new joke first
        arrayAdapter.notifyDataSetChanged()
        viewBinding.swiperefresh.isRefreshing = false
        viewBinding.textView3.visibility = if (arrayList.isEmpty()) View.VISIBLE else View.GONE
    }

    private val arrayAdapter by lazy {
        ArrayAdapter(this.requireContext(), R.layout.simple_list_item_1, arrayList)
    }

    // endregion

    // region -------- PRIVATE

    private val arrayList = ArrayList<String>()

    // endregion

}