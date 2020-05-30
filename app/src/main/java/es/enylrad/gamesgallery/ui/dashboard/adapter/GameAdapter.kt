package es.enylrad.gamesgallery.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import es.enylrad.gamesgallery.core.base.BasePagedAdapter
import es.enylrad.gamesgallery.core.model.GameEntity
import es.enylrad.gamesgallery.databinding.ItemGameBigBinding
import es.enylrad.gamesgallery.databinding.ItemGameNormalBinding
import es.enylrad.gamesgallery.databinding.ItemGameSimpleBinding
import es.enylrad.gamesgallery.ui.dashboard.DashboardViewModel
import es.enylrad.gamesgallery.ui.dashboard.adapter.sealed.GameTypeAdapter

class GameAdapter(
    private val viewModel: DashboardViewModel
) : BasePagedAdapter<GameEntity>(GameDiffCallback()) {

    private var mTypeAdapter: GameTypeAdapter = GameTypeAdapter.GridGameAdapter()

    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layout = when (viewType) {
            GameTypeAdapter.GridGameAdapter().type -> GameTypeAdapter.GridGameAdapter().layout
            GameTypeAdapter.CardGameAdapter().type -> GameTypeAdapter.CardGameAdapter().layout
            GameTypeAdapter.SimpleGameAdapter().type -> GameTypeAdapter.SimpleGameAdapter().layout
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
            is GameTypeAdapter.GridGameAdapter -> GameTypeAdapter.GridGameAdapter().type
            is GameTypeAdapter.CardGameAdapter -> GameTypeAdapter.CardGameAdapter().type
            is GameTypeAdapter.SimpleGameAdapter -> GameTypeAdapter.SimpleGameAdapter().type
        }
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        when (binding) {
            is ItemGameNormalBinding -> {
                binding.viewModel = viewModel
                binding.position = position
            }
            is ItemGameBigBinding -> {
                binding.viewModel = viewModel
                binding.position = position
            }
            is ItemGameSimpleBinding -> {
                binding.viewModel = viewModel
                binding.position = position
            }
        }
    }

    fun changeTypeAdapter(typeAdapter: GameTypeAdapter) {
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