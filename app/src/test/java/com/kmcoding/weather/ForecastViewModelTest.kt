package com.kmcoding.weather

import com.kmcoding.weather.data.source.FakeDataSource.fakeForecast
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.fake.FakeWeatherRepositoryImpl
import com.kmcoding.weather.ui.screens.forecast.ForecastViewModel
import com.kmcoding.weather.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ForecastViewModelTest {
    private lateinit var viewModel: ForecastViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = ForecastViewModel(weatherRepository = FakeWeatherRepositoryImpl())
    }

    @Test
    fun `verify forecast object after load`() =
        runTest {
            backgroundScope.launch(mainDispatcherRule.testDispatcher) {
                viewModel.fetchForecast(fakeLocations[0])
                viewModel.forecast.collect()
            }
            assertEquals(fakeForecast, viewModel.forecast.value)
        }
}
