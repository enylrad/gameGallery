package es.enylrad.gamesgallery.ui.profile

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.databinding.FragmentProfileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : BaseFragment() {

    override val viewModel by viewModel<ProfileViewModel>()

    override val binding by FragmentBinding<FragmentProfileBinding>(R.layout.fragment_profile)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.lifecycleOwner = this@ProfileFragment
            binding.viewModel = viewModel

            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.move)

            binding.lottieComingSoon.setOnClickListener {
                binding.lottieComingSoon.playAnimation()
            }
        }
    }
}