package com.kmcoding.weather

import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.ui.screens.search.SearchViewModel
import com.kmcoding.weather.util.MainDispatcherRule
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SearchViewModelTest {
    private lateinit var viewModel: SearchViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = SearchViewModel(weatherRepository = FakeWeatherRepositoryImpl())
    }

    @Test
    fun `verify if search is active after toggle`() =
        runTest {
            viewModel.toggleSearchActive()
            assertTrue(viewModel.isSearchActive.value)
            assertEquals("", viewModel.searchQuery.value)
        }

    @Test
    fun `verify if search query is invalid after entered number`() =
        runTest {
            viewModel.updateQuery("test1")
            assertFalse(viewModel.isQueryValidate())
        }

    @Test
    fun `verify if search query is invalid after entered sign`() =
        runTest {
            viewModel.updateQuery("test!")
            assertFalse(viewModel.isQueryValidate())
        }

    @Test
    fun `verify if search query is valid`() =
        runTest {
            viewModel.updateQuery("test")
            assertTrue(viewModel.isQueryValidate())
        }

    @Test
    fun `verify locations list size after entered query`() =
        runTest {
            viewModel.updateQuery("test")
            backgroundScope.launch(mainDispatcherRule.testDispatcher) {
                viewModel.fetchLocations()
            }
            assertEquals(fakeLocations.size, viewModel.locations.value.size)
        }

    @Test
    fun `verify if locations list is empty with empty query`() =
        runTest {
            backgroundScope.launch(mainDispatcherRule.testDispatcher) {
                viewModel.fetchLocations()
            }
            assertEquals(0, viewModel.locations.value.size)
        }

    @Test
    fun `verify if locations list is empty with invalid query`() =
        runTest {
            viewModel.updateQuery("test!")
            backgroundScope.launch(mainDispatcherRule.testDispatcher) {
                viewModel.fetchLocations()
            }
            assertEquals(0, viewModel.locations.value.size)
        }
}
