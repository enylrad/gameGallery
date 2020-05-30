package es.enylrad.gamesgallery.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.ConnectivityUtil
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.databinding.FragmentDashboardBinding
import es.enylrad.gamesgallery.ui.dashboard.adapter.GameAdapter
import es.enylrad.gamesgallery.ui.dashboard.adapter.sealed.GameTypeAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    override val viewModel by viewModel<DashboardViewModel>()

    override val binding by FragmentBinding<FragmentDashboardBinding>(R.layout.fragment_dashboard)

    private val adapter: GameAdapter by lazy {
        GameAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.lifecycleOwner = this@DashboardFragment
            binding.viewModel = viewModel

            setRecyclerView()
        }
    }

    private fun setRecyclerView() {
        viewModel.connectivityAvailable = ConnectivityUtil.isConnected(context)

        val initialColumnSize = GameTypeAdapter.GridGameAdapter().column

        binding.rvGames.adapter = adapter
        binding.rvGames.layoutManager = GridLayoutManager(context, initialColumnSize)
        binding.rvGames.layoutAnimation = AnimationUtils.loadLayoutAnimation(
            context,
            R.anim.grid_layout_animation_from_bottom
        )

        viewModel.selected.observe(viewLifecycleOwner) {
            Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
        }
        subscribeAdapter(adapter)
    }

    private fun subscribeAdapter(adapter: GameAdapter) {
        viewModel.gameAdapter.observe(viewLifecycleOwner) {
            binding.rvGames.layoutManager = GridLayoutManager(context, it.column)
            adapter.changeTypeAdapter(it)
        }
        viewModel.games.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}