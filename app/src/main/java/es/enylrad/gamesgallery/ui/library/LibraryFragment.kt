package es.enylrad.gamesgallery.ui.library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.databinding.FragmentLibraryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LibraryFragment : BaseFragment() {

    override val viewModel by viewModel<LibraryViewModel>()

    override val binding by FragmentBinding<FragmentLibraryBinding>(R.layout.fragment_library)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.lifecycleOwner = this@LibraryFragment
            binding.viewmodel = viewModel
        }
    }
}