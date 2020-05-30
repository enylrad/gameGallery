package es.enylrad.gamesgallery.ui.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.core.base.BasePagedAdapter
import es.enylrad.gamesgallery.core.model.GameEntity
import es.enylrad.gamesgallery.core.sealed.AdapterGameType
import es.enylrad.gamesgallery.databinding.ItemGameBigBinding
import es.enylrad.gamesgallery.databinding.ItemGameNormalBinding
import es.enylrad.gamesgallery.databinding.ItemGameSimpleBinding
import es.enylrad.gamesgallery.ui.dashboard.DashboardFragmentDirections

class GameAdapter : BasePagedAdapter<GameEntity>(GameDiffCallback()) {

    private var mTypeAdapter: AdapterGameType = AdapterGameType.GridGameAdapter()

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layout = when (viewType) {
            AdapterGameType.GridGameAdapter().type -> AdapterGameType.GridGameAdapter().layout
            AdapterGameType.CardGameAdapter().type -> AdapterGameType.CardGameAdapter().layout
            AdapterGameType.SimpleGameAdapter().type -> AdapterGameType.SimpleGameAdapter().layout
            else -> throw Exception("Layout not Implemented")
        }
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (mTypeAdapter) {
            is AdapterGameType.GridGameAdapter -> AdapterGameType.GridGameAdapter().type
            is AdapterGameType.CardGameAdapter -> AdapterGameType.CardGameAdapter().type
            is AdapterGameType.SimpleGameAdapter -> AdapterGameType.SimpleGameAdapter().type
        }
    }

    override fun createOnClickListener(
        item: GameEntity,
        extras: FragmentNavigator.Extras?
    ): View.OnClickListener {
        return View.OnClickListener { view ->
            val direction =
                DashboardFragmentDirections.actionNavigationDashboardToNavigationGameDetail(item)
            if (extras != null) {
                view.findNavController().navigate(direction, extras)
            } else {
                view.findNavController().navigate(direction)
            }
        }
    }

    override fun bind(
        binding: ViewDataBinding,
        item: GameEntity,
        context: Context
    ) {

        val imgTransition = "${context.getString(R.string.transition_img_game)}_${item.id}"
        val nameTransition = "${context.getString(R.string.transition_name_game)}_${item.id}"

        when (binding) {
            is ItemGameNormalBinding -> {
                setItemGameNormal(binding, item, imgTransition, nameTransition)
            }
            is ItemGameBigBinding -> {
                setItemGameBig(binding, item, imgTransition, nameTransition)
            }
            is ItemGameSimpleBinding -> {
                setItemGameSimple(binding, item, imgTransition, nameTransition)
            }
        }
    }

    private fun setItemGameNormal(
        binding: ItemGameNormalBinding,
        item: GameEntity,
        imgTransition: String,
        nameTransition: String
    ) {

        binding.ivCover.transitionName = imgTransition
        binding.tvName.transitionName = nameTransition

        val extras = FragmentNavigatorExtras(
            binding.ivCover to imgTransition,
            binding.tvName to nameTransition
        )
        binding.clickListener = createOnClickListener(item, extras)
        binding.game = item
    }

    private fun setItemGameBig(
        binding: ItemGameBigBinding,
        item: GameEntity,
        imgTransition: String,
        nameTransition: String
    ) {

        binding.ivCover.transitionName = imgTransition
        binding.tvName.transitionName = nameTransition

        val extras = FragmentNavigatorExtras(
            binding.ivCover to imgTransition,
            binding.tvName to nameTransition
        )
        binding.clickListener = createOnClickListener(item, extras)
        binding.game = item
    }

    private fun setItemGameSimple(
        binding: ItemGameSimpleBinding,
        item: GameEntity,
        imgTransition: String,
        nameTransition: String
    ) {

        binding.ivCover.transitionName = imgTransition
        binding.tvName.transitionName = nameTransition

        val extras = FragmentNavigatorExtras(
            binding.ivCover to imgTransition,
            binding.tvName to nameTransition
        )
        binding.clickListener = createOnClickListener(item, extras)
        binding.game = item
    }

    fun changeTypeAdapter(typeAdapter: AdapterGameType) {
        mTypeAdapter = typeAdapter
        notifyDataSetChanged()
    }
}

private class GameDiffCallback : DiffUtil.ItemCallback<GameEntity>() {

    override fun areItemsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
        return oldItem == newItem
    }
}