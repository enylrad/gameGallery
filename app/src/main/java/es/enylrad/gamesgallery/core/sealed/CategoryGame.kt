package es.enylrad.gamesgallery.core.sealed

import androidx.annotation.StringRes
import es.enylrad.gamesgallery.R

sealed class CategoryGame(val code: Int, @StringRes val name: Int) {
    class Main : CategoryGame(0, R.string.category_main_game)
    class DLCAddon : CategoryGame(1, R.string.category_dlc_addon)
    class Expansion : CategoryGame(2, R.string.category_expansion)
    class Bundle : CategoryGame(3, R.string.category_bundle)
    class StandaloneExpansion : CategoryGame(4, R.string.category_standalone_expansion)
    class Mod : CategoryGame(5, R.string.category_mod)
    class Episode : CategoryGame(6, R.string.category_episode)
    class Unknown : CategoryGame(-1, R.string.unknown)
}