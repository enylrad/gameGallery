package es.enylrad.gamesgallery.ui

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.model.utils.createUser
import es.enylrad.gamesgallery.commons.utils.ActivityBindingProperty
import es.enylrad.gamesgallery.core.base.BaseActivity
import es.enylrad.gamesgallery.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class MainActivity : BaseActivity() {

    companion object {
        const val RC_SIGN_IN = 1001
    }

    override val viewModel by viewModel<MainViewModel>()

    override val binding by ActivityBindingProperty<ActivityMainBinding>(R.layout.activity_main)

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var mGoogleAccount: GoogleSignInAccount? = null

    private val navController: NavController by lazy {
        findNavController(R.id.nav_host_fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setNavController()
        configGoogleSignInClient()
        configBtnProfile()
    }

    override fun onStart() {
        super.onStart()
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)
    }

    private fun setNavController() {
        binding.navView.setupWithNavController(navController)
    }

    private fun configBtnProfile() {
        binding.toolbar.btnProfile.setOnClickListener {
            signIn()
        }
    }

    private fun configGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        mGoogleAccount = account
        if (account != null) {
            val user = account.createUser()
            viewModel.updateUser(user)
        } else {
            Timber.d("No Updated")
            // TODO
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Timber.w("signInResult:failed code=${e.statusCode}")
            updateUI(null)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
        }
    }

}