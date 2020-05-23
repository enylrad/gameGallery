package es.enylrad.gamesgallery.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.databinding.FragmentDashboardBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : BaseFragment() {

    override val viewModel by viewModel<DashboardViewModel>()

    override val binding by FragmentBinding<FragmentDashboardBinding>(R.layout.fragment_dashboard)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.lifecycleOwner = this@DashboardFragment
            binding.viewmodel = viewModel
        }
    }
}