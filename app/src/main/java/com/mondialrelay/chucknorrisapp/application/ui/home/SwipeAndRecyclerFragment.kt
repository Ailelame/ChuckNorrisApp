package com.mondialrelay.chucknorrisapp.application.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mondialrelay.chucknorrisapp.databinding.FragmentSwipeAndRecyclerBinding
import com.mondialrelay.chucknorrisapp.databinding.RecyclerSimpleItemBinding
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import org.koin.android.ext.android.inject

class SwipeAndRecyclerFragment : Fragment() {

    private val viewModel: SwipeAndRecyclerViewModel by inject()
    private lateinit var viewBinding: FragmentSwipeAndRecyclerBinding
    private lateinit var rvAdapter: OtherAdapter

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentSwipeAndRecyclerBinding.inflate(layoutInflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter = OtherAdapter()
        viewBinding.recyclerView.adapter = rvAdapter
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }

        initObserversListeners()

        uiShowToast()
    }

    // endregion

    // region -------- UI

    private fun initObserversListeners() {
        with(viewModel) {
            liveJokesList.observe(viewLifecycleOwner) { list ->
                uiUpdateJokes(list)
            }
        }
        with(viewBinding) {
            swiperefresh.setOnRefreshListener {
                viewModel.actionAddJoke.invoke()
            }
            floatingActionButton.setOnClickListener {
                viewModel.actionClear.invoke()
            }
        }
        rvAdapter.onClick.observe(viewLifecycleOwner) {
            when (it) {
                is OtherAdapterActions.OtherAdapterRatingAction -> {
                    viewModel.actionRate(it.joke.id, it.newRating)
                }
                is OtherAdapterActions.OtherAdapterItemClick -> {
                    Toast.makeText(requireContext(), "${it.joke.text}", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }

    }

    private fun uiUpdateJokes(jokesList: List<JokeModel>) {
        rvAdapter.setItems(jokesList)

        if (jokesList.isEmpty()) uiShowToast()

        viewBinding.swiperefresh.isRefreshing = false
    }

    private fun uiShowToast() {
        Toast.makeText(context, "Swipe down to fetch a joke", Toast.LENGTH_SHORT).show()
    }


    // endregion

}


class OtherAdapter : RecyclerView.Adapter<RV2ViewHolder>() {

    private val list = mutableListOf<JokeModel>()

    private val _onClick = MutableLiveData<OtherAdapterActions>()
    val onClick: LiveData<OtherAdapterActions> = _onClick

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RV2ViewHolder {
        return RV2ViewHolder(
            RecyclerSimpleItemBinding.inflate(
                LayoutInflater
                    .from(parent.context), parent, false
            ), _onClick
        )
    }

    override fun onBindViewHolder(holder: RV2ViewHolder, position: Int) {
        val joke = list.getOrNull(position)
        joke?.let { joke ->
            holder.onBind(joke)
            holder.binding.root.setOnClickListener {
                _onClick.postValue(OtherAdapterActions.OtherAdapterItemClick(joke))
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setItems(items: List<JokeModel>) {
        this.list.clear()
        this.list.addAll(items)
        notifyDataSetChanged()
    }

}


class RV2ViewHolder(
    val binding: RecyclerSimpleItemBinding,
    val onClick: MutableLiveData<OtherAdapterActions>
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(joke: JokeModel) {
        binding.textView.text = joke.text
        binding.ratingBar.rating = joke.rating
        binding.txDate.text = joke.createdAt
        binding.txId.text = joke.id
        binding.ratingBar.setOnRatingBarChangeListener { _, fl, fromTouch ->
            if (fromTouch) onClick.postValue(OtherAdapterActions.OtherAdapterRatingAction(joke, fl))
        }

    }
}

sealed interface OtherAdapterActions {
    class OtherAdapterRatingAction(val joke: JokeModel, val newRating: Float) : OtherAdapterActions
    class OtherAdapterItemClick(val joke: JokeModel) : OtherAdapterActions
}

