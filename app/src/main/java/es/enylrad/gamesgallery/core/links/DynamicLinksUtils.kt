package es.enylrad.gamesgallery.core.links

import android.content.Context
import android.net.Uri
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import es.enylrad.gamesgallery.R
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun Context.createInviteFriendLink(idUser: String): ShortDynamicLink =
    suspendCancellableCoroutine { c ->
        FirebaseDynamicLinks.getInstance()
            .createDynamicLink()
            .setLink(Uri.parse("${getString(R.string.url_dynamic_link_web)}?idFriend=${idUser}"))
            .setDomainUriPrefix(getString(R.string.url_dynamic_link_share))
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .buildShortDynamicLink()
            .addOnSuccessListener { link ->
                Timber.d("createDynamicLink: success ${link.shortLink.toString()}")
                c.resume(link)
            }
            .addOnFailureListener { exception ->
                Timber.e("createDynamicLink: failure $exception")
                c.resumeWithException(exception)
            }
    }