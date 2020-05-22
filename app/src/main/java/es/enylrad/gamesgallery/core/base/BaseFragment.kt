package es.enylrad.gamesgallery.core.base

import androidx.fragment.app.Fragment
import es.enylrad.gamesgallery.MainActivity

abstract class BaseFragment : Fragment() {

    fun mainActivity(): MainActivity? {
        return activity as? MainActivity
    }
}