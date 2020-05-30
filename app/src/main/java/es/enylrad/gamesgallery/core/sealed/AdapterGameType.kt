package es.enylrad.gamesgallery.core.sealed

import androidx.annotation.IntRange
import androidx.annotation.LayoutRes
import es.enylrad.gamesgallery.R

sealed class AdapterGameType(
    @LayoutRes
    val layout: Int,
    val type: Int,
    @IntRange(from = 1, to = 3)
    val column: Int
) {
    class GridGameAdapter : AdapterGameType(R.layout.item_game_normal, 0, 2)
    class CardGameAdapter : AdapterGameType(R.layout.item_game_big, 1, 1)
    class SimpleGameAdapter : AdapterGameType(R.layout.item_game_simple, 2, 1)
}