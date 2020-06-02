package es.enylrad.gamesgallery.core.sealed

import androidx.annotation.StringRes
import es.enylrad.gamesgallery.R

sealed class StatusGame(val code: Int, @StringRes val name: Int) {
    class Released : StatusGame(0, R.string.status_released)
    class Alpha : StatusGame(1, R.string.status_alpha)
    class Beta : StatusGame(2, R.string.status_beta)
    class EarlyAccess : StatusGame(3, R.string.status_early_access)
    class Offline : StatusGame(4, R.string.status_offline)
    class Cancelled : StatusGame(5, R.string.status_cancelled)
    class Rumored : StatusGame(6, R.string.status_rumored)
    class Unknown : StatusGame(-1, R.string.unknown)
}