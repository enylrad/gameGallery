package es.enylrad.gamesgallery.commons.utils

import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author https://medium.com/@dev.dhar/get-rid-of-lateinit-var-when-using-databinding-84bb06ec1c0a
 */
class FragmentBinding<out T : ViewDataBinding>(
    @LayoutRes private val resId: Int
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    override operator fun getValue(
        thisRef: Fragment,
        property: KProperty<*>
    ): T = binding ?: createBinding(thisRef).also { binding = it }

    private fun createBinding(
        activity: Fragment
    ): T = DataBindingUtil.inflate(LayoutInflater.from(activity.context), resId, null, true)
}

/**
 * @author https://medium.com/@dev.dhar/get-rid-of-lateinit-var-when-using-databinding-84bb06ec1c0a
 */
class ActivityBindingProperty<out T : ViewDataBinding>(
    @LayoutRes private val resId: Int
) : ReadOnlyProperty<AppCompatActivity, T> {

    private var binding: T? = null

    override operator fun getValue(
        thisRef: AppCompatActivity,
        property: KProperty<*>
    ): T = binding ?: createBinding(thisRef).also { binding = it }

    private fun createBinding(
        activity: AppCompatActivity
    ): T = DataBindingUtil.setContentView(activity, resId)
}