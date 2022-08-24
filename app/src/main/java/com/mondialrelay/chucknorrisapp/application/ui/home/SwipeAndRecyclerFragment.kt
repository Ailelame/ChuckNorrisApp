package com.mondialrelay.chucknorrisapp.application.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mondialrelay.chucknorrisapp.R
import com.mondialrelay.chucknorrisapp.databinding.FragmentSwipeAndRecyclerBinding
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import org.koin.android.ext.android.inject

class SwipeAndRecyclerFragment : Fragment() {

    private val viewModel: SwipeAndRecyclerViewModel by inject()
    private lateinit var viewBinding: FragmentSwipeAndRecyclerBinding

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rvAdapter = RVAdapter(viewModel)

        viewBinding = FragmentSwipeAndRecyclerBinding.inflate(layoutInflater, container, false)
        viewBinding.recyclerView.adapter = rvAdapter
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(context).apply {
            orientation = RecyclerView.VERTICAL
        }
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserversListeners()
        uiShowToast()
    }

    // endregion

    // region -------- UI

    private fun initObserversListeners() {
        with(viewModel) {
            liveJokesList.observe(viewLifecycleOwner) { (list, position) ->
                uiUpdateJokes(list, position)
            }
        }
        with(viewBinding) {
            swiperefresh.setOnRefreshListener {
                viewModel.actionRefresh.invoke()
            }
            floatingActionButton.setOnClickListener {
                viewModel.actionClear.invoke()
            }
        }
    }

    private fun uiUpdateJokes(jokesList: List<JokeModel>, position: Int?) {
        when {
            jokesList.isEmpty() -> {
                rvAdapter.notifyItemRangeRemoved(0, rvAdapter.list.size)
                rvAdapter.list.clear()
                uiShowToast()
            }
            jokesList.size > rvAdapter.list.size -> {
                rvAdapter.list.clear()
                rvAdapter.list.addAll(0, jokesList)
                rvAdapter.notifyItemInserted(position ?: 0)
                viewBinding.recyclerView.scrollToPosition(position ?: 0)
            }
            else ->
                rvAdapter.notifyItemChanged(position ?: 0)
        }
        viewBinding.swiperefresh.isRefreshing = false
    }

    private fun uiShowToast() {
        Toast.makeText(context, "Swipe down to fetch a joke", Toast.LENGTH_SHORT).show()
    }

    private lateinit var rvAdapter: RVAdapter

    // endregion

}

class RVAdapter(
    val viewModel: SwipeAndRecyclerViewModel,
    val list: MutableList<JokeModel> = mutableListOf(),
) : RecyclerView.Adapter<RVAdapter.RVViewHolder>() {

    class RVViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textView)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val textId: TextView = view.findViewById(R.id.txId)
        val textDate: TextView = view.findViewById(R.id.txDate)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RVViewHolder {
        return LayoutInflater
            .from(viewGroup.context)
            .inflate(R.layout.recycler_simple_item, viewGroup, false)
            .let { view ->
                RVViewHolder(view)
                    .apply {
                        view.findViewById<RatingBar>(R.id.ratingBar)
                            .setOnRatingBarChangeListener { _, fl, _ ->
                                viewModel.actionRate.invoke(this.adapterPosition, fl)
                            }
                    }
            }
    }

    override fun onBindViewHolder(viewHolder: RVViewHolder, position: Int) {
        viewHolder.textView.text = list[position].text
        viewHolder.ratingBar.rating = list[position].rating
        viewHolder.textId.text = list[position].id
        viewHolder.textDate.text = list[position].createdAt
    }

    override fun getItemCount() = list.size

}


