package com.mondialrelay.chucknorrisapp.application.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mondialrelay.chucknorrisapp.R
import com.mondialrelay.chucknorrisapp.databinding.FragmentSwipeAndRecyclerBinding
import com.mondialrelay.chucknorrisapp.domain.model.JokeModel
import org.koin.android.ext.android.get

class SwipeAndRecyclerFragment : Fragment() {

    private lateinit var viewModel: SwipeAndRecyclerViewModel
    private lateinit var viewBinding: FragmentSwipeAndRecyclerBinding

    // region -------- INITIALISATION

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[SwipeAndRecyclerViewModel::class.java]
        viewModel.jokeApi = get()

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
    }

    // endregion

    // region -------- UI

    private fun initObserversListeners() {
        with(viewModel) {
            liveJokesList.observe(viewLifecycleOwner) { (list, position) ->
                if (list != null)
                    uiUpdateJokes(list, position!!)
                else
                    uiClearJokes()
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

    private fun uiUpdateJokes(jokesList: List<JokeModel>, insertPosition: Int) {
        rvAdapter.list.clear()
        rvAdapter.list.addAll(0, jokesList)
        rvAdapter.notifyItemInserted(insertPosition)
        viewBinding.recyclerView.scrollToPosition(0)
        viewBinding.swiperefresh.isRefreshing = false
    }

    private fun uiClearJokes() {
        rvAdapter.notifyItemRangeRemoved(0, rvAdapter.list.size)
        rvAdapter.list.clear()
    }

    private val rvAdapter = RVAdapter()

    // endregion

}

class RVAdapter(
    val list: MutableList<JokeModel> = mutableListOf()
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


