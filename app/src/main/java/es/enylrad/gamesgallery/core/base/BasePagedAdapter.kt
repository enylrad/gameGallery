package es.enylrad.gamesgallery.core.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.navigation.fragment.FragmentNavigator
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BasePagedAdapter<T>(
    diffCallback: DiffUtil.ItemCallback<T>
) : PagedListAdapter<T, BaseViewHolder>(
    AsyncDifferConfig.Builder<T>(diffCallback)
        .build()
) {

    lateinit var recyclerView: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val binding = createBinding(parent, viewType)
        return BaseViewHolder(binding)
    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            holder.apply {
                bind(holder.binding, item, holder.itemView.context)
                holder.binding.executePendingBindings()
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    protected abstract fun createOnClickListener(
        item: T,
        extras: FragmentNavigator.Extras?
    ): View.OnClickListener

    protected abstract fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding

    protected abstract fun bind(binding: ViewDataBinding, item: T, context: Context)

}