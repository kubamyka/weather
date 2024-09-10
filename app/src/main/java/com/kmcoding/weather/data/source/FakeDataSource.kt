package com.kmcoding.weather.data.source

import com.kmcoding.weather.domain.model.Location

object FakeDataSource {
    val fakeLocations =
        listOf(
            Location(id = 1, name = "Wrocław", country = "Poland", url = "wroclaw-poland"),
            Location(id = 2, name = "Nowy Sącz", country = "Poland", url = "nowy-sacz-poland"),
            Location(id = 3, name = "Kielce", country = "Poland", url = "kielce-poland"),
        )
}
