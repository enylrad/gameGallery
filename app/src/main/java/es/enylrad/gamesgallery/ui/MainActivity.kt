package es.enylrad.gamesgallery.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import es.enylrad.gamesgallery.R
import es.enylrad.gamesgallery.commons.utils.ActivityBindingProperty
import es.enylrad.gamesgallery.core.base.BaseActivity
import es.enylrad.gamesgallery.core.model.UserEntity
import es.enylrad.gamesgallery.core.model.utils.createUser
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
    private var mAuth: FirebaseAuth? = null

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
        configFirebaseAuth()
        setListenerChangeFragment()
        configBtnProfile()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            getDynamicLink(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val account: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)
        if (account != null) {
            mAuth?.currentUser?.let {
                updateUI(account)
            } ?: signOut()
        } else {
            updateUI(null)
        }

    }

    private fun setNavController() {
        binding.navView.setupWithNavController(navController)
    }

    private fun configBtnProfile() {
        binding.toolbar.btnProfile.setOnClickListener {
            if (mAuth?.currentUser == null) {
                signIn()
            } else {
                launchProfile()
            }
        }
    }

    private fun launchProfile() {
        navController.navigate(R.id.navigation_profile)
    }

    private fun configGoogleSignInClient() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun configFirebaseAuth() {
        mAuth = FirebaseAuth.getInstance()
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        mGoogleAccount = account
        if (account != null) {
            val user = account.createUser()
            viewModel.refreshUser(user)
        } else {
            val user = UserEntity()
            viewModel.refreshUser(user)
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account!!)
        } catch (e: ApiException) {
            snackBar(getString(R.string.fail_auth))
            Timber.w("signInResult:failed code=${e.statusCode}")
            updateUI(null)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Timber.d("firebaseAuthWithGoogle: ${account.id}")

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth
            ?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("signInWithCredential: success")
                    val user = mAuth!!.currentUser!!
                    viewModel.createUser(account.createUser(), user.uid)
                    updateUI(account)

                } else {
                    snackBar(getString(R.string.fail_auth))
                    Timber.w("signInWithCredential: failure ${task.exception}")
                    updateUI(null)
                }
            } ?: updateUI(null)
    }

    private fun snackBar(text: String) {
        Snackbar.make(binding.container, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        mGoogleSignInClient?.signOut()
            ?.addOnCompleteListener(this) {
                updateUI(null)
            }
    }

    private fun setListenerChangeFragment() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_game_detail,
                R.id.navigation_profile -> {
                    visibilityToolbar(View.GONE)
                    visibilityNavigationBar(View.GONE)
                    visibilityBoxSearch(View.GONE)
                }
                R.id.navigation_dashboard -> {
                    visibilityToolbar(View.VISIBLE)
                    visibilityNavigationBar(View.VISIBLE)
                    visibilityBoxSearch(View.VISIBLE)

                }
                R.id.navigation_library -> {
                    visibilityToolbar(View.VISIBLE)
                    visibilityNavigationBar(View.VISIBLE)
                    visibilityBoxSearch(View.GONE)
                }
                else -> {
                    visibilityToolbar(View.VISIBLE)
                    visibilityNavigationBar(View.VISIBLE)
                    visibilityBoxSearch(View.GONE)
                }
            }
        }
    }

    private fun visibilityToolbar(visibility: Int) {
        binding.toolbar.toolbarApp.visibility = visibility
    }

    private fun visibilityNavigationBar(visibility: Int) {
        binding.navView.visibility = visibility
    }

    private fun visibilityBoxSearch(visibility: Int) {
        binding.toolbar.svSearchGames.visibility = visibility
    }

    private fun getDynamicLink(intent: Intent) {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener { pendingDynamicLinkData ->
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                }
                // TODO
                Timber.d(deepLink.toString())
            }
            .addOnFailureListener { exception ->
                Timber.e("getDynamicLink: OnFailure $exception")
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