package es.enylrad.gamesgallery.core.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import es.enylrad.gamesgallery.MainActivity

abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: ViewModel
    protected abstract val binding: ViewBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    fun mainActivity(): MainActivity? {
        return activity as? MainActivity
    }
}