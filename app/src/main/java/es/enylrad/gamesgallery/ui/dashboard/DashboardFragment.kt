package es.enylrad.gamesgallery.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.databinding.FragmentDashboardBinding
import es.enylrad.gamesgallery.ui.dashboard.adapter.GameAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    override val viewModel by viewModel<DashboardViewModel>()

    override val binding by FragmentBinding<FragmentDashboardBinding>(R.layout.fragment_dashboard)

    private lateinit var adapter: GameAdapter

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

        adapter = GameAdapter(viewModel)
        binding.rvGames.adapter = adapter
        viewModel.typeAdapter.observe(viewLifecycleOwner,
            Observer {
                binding.rvGames.layoutManager = GridLayoutManager(context, it.column)
                adapter.changeTypeAdapter(it)
            })
        viewModel.games.observe(viewLifecycleOwner,
            Observer {
                binding.rvGames.layoutAnimation = AnimationUtils.loadLayoutAnimation(
                    context,
                    R.anim.grid_layout_animation_from_bottom
                )
                adapter.submitList(it)
            })

        viewModel.selected.observe(viewLifecycleOwner,
            Observer {
                Toast.makeText(context, it.toString(), Toast.LENGTH_SHORT).show()
            })
    }
}