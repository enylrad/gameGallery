package es.enylrad.gamesgallery.ui.game

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.navArgs
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.FragmentBinding
import es.enylrad.gamesgallery.core.base.BaseFragment
import es.enylrad.gamesgallery.databinding.FragmentGameDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameDetailFragment : BaseFragment() {

    override val viewModel by viewModel<GameDetailViewModel>()

    override val binding by FragmentBinding<FragmentGameDetailBinding>(R.layout.fragment_game_detail)

    private val args: GameDetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState).apply {

            binding.lifecycleOwner = this@GameDetailFragment
            viewModel.game = args.game
            binding.viewModel = viewModel

            val imageTransition = "${getString(R.string.transition_img_game)}_${viewModel.game.id}"
            val nameTransition = "${getString(R.string.transition_name_game)}_${viewModel.game.id}"

            binding.ivCover.transitionName = imageTransition
            binding.tvName.transitionName = nameTransition

            ViewCompat.setTransitionName(binding.ivCover, imageTransition)
            ViewCompat.setTransitionName(binding.tvName, nameTransition)
        }
    }
}