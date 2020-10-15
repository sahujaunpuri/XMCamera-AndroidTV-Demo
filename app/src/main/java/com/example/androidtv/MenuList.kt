package com.example.androidtv

object MenuList {
    val MENU_CATEGORY = arrayOf(
            "1. User",
            "2. Device",
            "3. Media",
            "4. Play URL")

    val list: List<Menu> by lazy {
        setupMovies()
    }
    private var count: Long = 0

    private fun setupMovies(): List<Menu> {
        val title = arrayOf(
                "1.1 Register",
                "2.4 Add by User",
                "1.2 Login",
                "2.7 User's List",
                "1.4 Forgot",
                "1.5 User Info",
                "4.1 Play URL")
        val category = arrayOf(
            1,
            2,
            1,
            2,
            1,
            1,
            4
        )
        val footer = arrayOf(
                "User",
                "Device",
                "User",
                "Device",
                "User",
                "User",
                "Play URL")
        val videoUrl = arrayOf(
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search.mp4"
        )
        val bgImageUrl = arrayOf(
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/bg.jpg",
        )
        val cardImageUrl = arrayOf(
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
                "http://commondatastorage.googleapis.com/android-tv/Sample%20videos/Demo%20Slam/Google%20Demo%20Slam_%2020ft%20Search/card.jpg",
        )

        val list = title.indices.map {
            buildMenuInfo(
                    title[it],
                    category[it],
                    footer[it],
                    videoUrl[it],
                    cardImageUrl[it],
                    bgImageUrl[it])
        }

        return list
    }

    private fun buildMenuInfo(
            title: String,
            category: Int,
            footer: String,
            videoUrl: String,
            cardImageUrl: String,
            backgroundImageUrl: String): Menu {
        val menu = Menu()
        menu.id = count++
        menu.category = category
        menu.title = title
        menu.footer = footer
        menu.cardImageUrl = cardImageUrl
        menu.backgroundImageUrl = backgroundImageUrl
        menu.videoUrl = videoUrl
        return menu
    }
}
