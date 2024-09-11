package com.kmcoding.weather.data.source

import com.kmcoding.weather.domain.model.Condition
import com.kmcoding.weather.domain.model.CurrentWeather
import com.kmcoding.weather.domain.model.Forecast
import com.kmcoding.weather.domain.model.ForecastHour
import com.kmcoding.weather.domain.model.ForecastObj
import com.kmcoding.weather.domain.model.ForecastResponse
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

    val fakeHours =
        listOf(
            ForecastHour(
                timestamp = 1725995923,
                date = "2024-09-11 11:15",
                celsiusTemperature = 18.2,
                condition = fakeConditions[0],
                windKph = 20.2,
                humidity = 40,
                cloud = 0,
            ),
            ForecastHour(
                timestamp = 1725995924,
                date = "2024-09-11 12:15",
                celsiusTemperature = 22.2,
                condition = fakeConditions[1],
                windKph = 20.2,
                humidity = 80,
                cloud = 50,
            ),
            ForecastHour(
                timestamp = 1724995925,
                date = "2024-09-11 13:15",
                celsiusTemperature = 21.3,
                condition = fakeConditions[2],
                windKph = 20.2,
                humidity = 0,
                cloud = 100,
            ),
            ForecastHour(
                timestamp = 1724995923,
                date = "2024-09-11 14:15",
                celsiusTemperature = 18.2,
                condition = fakeConditions[0],
                windKph = 20.2,
                humidity = 40,
                cloud = 0,
            ),
            ForecastHour(
                timestamp = 1735995924,
                date = "2024-09-11 15:15",
                celsiusTemperature = 22.2,
                condition = fakeConditions[1],
                windKph = 20.2,
                humidity = 80,
                cloud = 50,
            ),
            ForecastHour(
                timestamp = 1725915925,
                date = "2024-09-11 16:15",
                celsiusTemperature = 21.3,
                condition = fakeConditions[2],
                windKph = 20.2,
                humidity = 0,
                cloud = 100,
            ),
            ForecastHour(
                timestamp = 1722395923,
                date = "2024-09-11 17:15",
                celsiusTemperature = 18.2,
                condition = fakeConditions[0],
                windKph = 20.2,
                humidity = 40,
                cloud = 0,
            ),
            ForecastHour(
                timestamp = 1725993224,
                date = "2024-09-11 18:15",
                celsiusTemperature = 2.2,
                condition = fakeConditions[1],
                windKph = 20.2,
                humidity = 80,
                cloud = 50,
            ),
            ForecastHour(
                timestamp = 1725995125,
                date = "2024-09-11 19:15",
                celsiusTemperature = -21.3,
                condition = fakeConditions[2],
                windKph = 20.2,
                humidity = 0,
                cloud = 100,
            ),
        )

    val fakeForecastObj = ForecastObj(date = "2024-09-11", timestamp = 1725995929, hours = fakeHours)
    val fakeForecast = Forecast(current = fakeWeathers[0], forecast = ForecastResponse(forecastObj = listOf(fakeForecastObj)))
}
