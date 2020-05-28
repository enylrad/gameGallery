package es.enylrad.gamesgallery.core.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity : AppCompatActivity() {

    protected abstract val viewModel: ViewModel
    protected abstract val binding: ViewBinding

    fun findNavController(@IdRes container: Int): NavController {
        return (supportFragmentManager.findFragmentById(container) as NavHostFragment).navController
    }
}