package es.enylrad.gamesgallery.commons.utils

import es.enylrad.gamesgallery.core.model.CoverEntity
import es.enylrad.gamesgallery.core.model.GameEntity
import es.enylrad.gamesgallery.core.sealed.CategoryGame
import es.enylrad.gamesgallery.core.sealed.StatusGame

val testGameA = GameEntity(
    id = 0,
    name = "Game A",
    summary = "Summary Game A",
    cover = CoverEntity(),
    created_at = 0,
    updated_at = 1,
    screenshots = listOf(),
    popularity = 0.0,
    aggregated_rating = 0.0,
    first_release_date = 0,
    total_rating = 0.0,
    storyline = null,
    status = StatusGame.Unknown().code,
    category = CategoryGame.Unknown().code
)

val testGameB = GameEntity(
    id = 0,
    name = "Game B",
    summary = "Summary Game B",
    cover = CoverEntity(),
    created_at = 0,
    updated_at = 1,
    screenshots = listOf(),
    popularity = 0.0,
    aggregated_rating = 0.0,
    first_release_date = 0,
    total_rating = 0.0,
    storyline = null,
    status = StatusGame.Unknown().code,
    category = CategoryGame.Unknown().code
)