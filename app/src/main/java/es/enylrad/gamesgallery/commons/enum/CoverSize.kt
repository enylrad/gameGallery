package es.enylrad.gamesgallery.commons.enum

sealed class CoverSize(val id: String) {
    class CoverSmall : CoverSize("cover_small") // 90 x 128
    class ScreenShotMed : CoverSize("screenshot_med") // 569 x 320
    class CoverBig : CoverSize("cover_big") // 264 x 374
    class LogoMed : CoverSize("logo_med") // 284 x 160
    class ScreenShotBig : CoverSize("screenshot_big") // 889 x 500
    class ScreenShotHuge : CoverSize("screenshot_huge") // 1280 x 720
    class Thumb : CoverSize("thumb") // 90 x 90
    class Micro : CoverSize("micro") // 35 x 35
    class MD : CoverSize("720p") // 1280 x 720
    class HD : CoverSize("1080") // 1920 x 1080
}