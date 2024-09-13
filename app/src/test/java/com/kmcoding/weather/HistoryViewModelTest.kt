package com.kmcoding.weather

import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.fake.FakeLocationDaoImpl
import com.kmcoding.weather.ui.screens.history.HistoryViewModel
import com.kmcoding.weather.util.MainDispatcherRule
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HistoryViewModelTest {
    private lateinit var viewModel: HistoryViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = HistoryViewModel(locationDao = FakeLocationDaoImpl())
    }

    @Test
    fun `verify history locations list size after load`() =
        runTest {
            backgroundScope.launch(mainDispatcherRule.testDispatcher) {
                viewModel.locations.collect()
            }
            assertEquals(fakeLocations.size, viewModel.locations.value.size)
        }
}
