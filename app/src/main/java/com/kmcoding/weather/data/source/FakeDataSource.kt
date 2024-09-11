package com.kmcoding.weather.data.source

import com.kmcoding.weather.domain.model.Condition
import com.kmcoding.weather.domain.model.CurrentWeather
import com.kmcoding.weather.domain.model.Location

object FakeDataSource {
    val fakeLocations =
        listOf(
            Location(id = 1, name = "Wrocław", country = "Poland", url = "wroclaw-poland", 1725995923),
            Location(id = 2, name = "Nowy Sącz", country = "Poland", url = "nowy-sacz-poland", 1726045674),
            Location(id = 3, name = "Kielce", country = "Poland", url = "kielce-poland", 1726045682),
        )
    val fakeConditions =
        listOf(
            Condition(text = "Słonecznie", icon = "//cdn.weatherapi.com/weather/64x64/day/113.png"),
            Condition(text = "Częściowe zachmurzenie", icon = "//cdn.weatherapi.com/weather/64x64/day/116.png"),
            Condition(text = "Zamglenie", icon = "//cdn.weatherapi.com/weather/64x64/night/143.png"),
        )

    val fakeWeathers =
        listOf(
            CurrentWeather(
                lastUpdated = "2024-09-11 11:15",
                celsiusTemperature = 18.2,
                condition = fakeConditions[0],
                windKph = 20.2,
                humidity = 40,
                cloud = 0,
            ),
            CurrentWeather(
                lastUpdated = "2024-09-11 12:15",
                celsiusTemperature = 22.2,
                condition = fakeConditions[1],
                windKph = 20.2,
                humidity = 80,
                cloud = 50,
            ),
            CurrentWeather(
                lastUpdated = "2024-09-11 13:15",
                celsiusTemperature = 21.3,
                condition = fakeConditions[2],
                windKph = 20.2,
                humidity = 0,
                cloud = 100,
            ),
        )
}
