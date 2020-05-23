package es.enylrad.gamesgallery.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.model.GameEntity
import es.enylrad.gamesgallery.core.base.BaseAdapter
import es.enylrad.gamesgallery.databinding.ItemGameBinding
import es.enylrad.gamesgallery.ui.dashboard.DashboardViewModel

class GameAdapter(
    private val viewModel: DashboardViewModel
) : BaseAdapter<GameEntity>(
    diffCallback = object : DiffUtil.ItemCallback<GameEntity>() {
        override fun areItemsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GameEntity, newItem: GameEntity): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_game,
            parent,
            false
        )
    }

    override fun bind(binding: ViewDataBinding, position: Int) {
        when (binding) {
            is ItemGameBinding -> {
                binding.viewModel = viewModel
                binding.position = position
            }
        }
    }
}
