package es.enylrad.gamesgallery.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.ConnectivityUtil
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.core.sealed.AdapterGameType
import es.enylrad.gamesgallery.databinding.FragmentDashboardBinding
import es.enylrad.gamesgallery.ui.dashboard.adapter.GameAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    companion object {
        const val STATE_RV_GAMES = "state_rv_games"
    }

    override val viewModel by viewModel<DashboardViewModel>()

    override val binding by FragmentBinding<FragmentDashboardBinding>(R.layout.fragment_dashboard)

    private val adapter: GameAdapter by lazy {
        GameAdapter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(STATE_RV_GAMES, binding.rvGames.layoutManager?.onSaveInstanceState())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            binding.rvGames.layoutManager?.onRestoreInstanceState(
                savedInstanceState.getParcelable(
                    STATE_RV_GAMES
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.lifecycleOwner = this@DashboardFragment
            binding.viewModel = viewModel

            setConnectivity()
            setRecyclerView()
        }
    }

    private fun setConnectivity() {
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context)
    }

    private fun setRecyclerView() {

        val sizeColumn = AdapterGameType.GridGameAdapter().column
        binding.rvGames.layoutManager = GridLayoutManager(context, sizeColumn)
        binding.rvGames.adapter = adapter

        postponeEnterTransition()
        binding.rvGames.viewTreeObserver.addOnDrawListener {
            startPostponedEnterTransition()
        }

        viewModel.gameAdapter.observe(viewLifecycleOwner) { type ->
            updateAdapter(type)
        }

        viewModel.games.observe(viewLifecycleOwner) { games ->
            adapter.submitList(games)
        }
    }

    private fun updateAdapter(type: AdapterGameType) {
        var scrollPosition = 0
        // If a layout manager has already been set, get current scroll position.
        if (binding.rvGames.layoutManager != null) {
            scrollPosition = (binding.rvGames.layoutManager as GridLayoutManager)
                .findFirstCompletelyVisibleItemPosition()
        }
        binding.rvGames.layoutManager = GridLayoutManager(context, type.column)

        adapter.changeTypeAdapter(type)

        binding.rvGames.scrollToPosition(scrollPosition)
    }
}