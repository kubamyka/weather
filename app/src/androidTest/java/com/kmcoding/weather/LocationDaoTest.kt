package com.kmcoding.weather

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmcoding.weather.data.db.AppDatabase
import com.kmcoding.weather.data.db.LocationDao
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.model.Location
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationDaoTest {
    private lateinit var database: AppDatabase
    private lateinit var locationDao: LocationDao

    @Before
    fun setupDatabase() {
        database =
            Room
                .inMemoryDatabaseBuilder(
                    ApplicationProvider.getApplicationContext(),
                    AppDatabase::class.java,
                ).allowMainThreadQueries()
                .build()

        locationDao = database.locationDao()
    }

    @After
    @Throws(Exception::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertLocation_success() =
        runBlocking {
            locationDao.insert(fakeLocations[0])
            val result = locationDao.getHistoryLocations().first()
            assertTrue(result.contains(fakeLocations[0]))
        }

    @Test
    @Throws(Exception::class)
    fun deleteLocation_success() =
        runBlocking {
            locationDao.insert(fakeLocations[0])
            locationDao.delete(fakeLocations[0])
            val result = locationDao.getHistoryLocations().first()
            assertFalse(result.contains(fakeLocations[0]))
        }

    @Test
    @Throws(Exception::class)
    fun updateLocation_success() =
        runBlocking {
            locationDao.insert(fakeLocations[0])

            val updatedLocation =
                Location(id = 1, name = "Poznań", country = "Poland", url = "poznan-poland", 1725995923)
            locationDao.update(updatedLocation)

            val result = locationDao.getLocation(updatedLocation.id).first()
            assertEquals(result.name, updatedLocation.name)
        }

    @Test
    @Throws(Exception::class)
    fun insertAllLocations_success() =
        runBlocking {
            locationDao.insertAll(fakeLocations)
            val result = locationDao.getHistoryLocations().first()
            assertEquals(result.size, fakeLocations.size)
        }

    @Test
    @Throws(Exception::class)
    fun deleteAllLocations_success() =
        runBlocking {
            locationDao.insertAll(fakeLocations)
            locationDao.deleteAllLocations()
            val result = locationDao.getHistoryLocations().first()
            assertEquals(result.size, 0)
        }
}
