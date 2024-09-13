package com.kmcoding.weather

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kmcoding.weather.data.db.AppDatabase
import com.kmcoding.weather.data.db.LocationDao
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.model.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

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
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertLocation_success() =
        runBlocking {
            locationDao.insert(fakeLocations[0])

            val latch = CountDownLatch(1)
            val job =
                async(Dispatchers.IO) {
                    locationDao.getHistoryLocations().collect { result ->
                        assertTrue(result.contains(fakeLocations[0]))
                        latch.countDown()
                    }
                }
            latch.await()
            job.cancelAndJoin()
        }

    @Test
    fun deleteLocation_success() =
        runBlocking {
            locationDao.insert(fakeLocations[0])
            locationDao.delete(fakeLocations[0])

            val latch = CountDownLatch(1)
            val job =
                async(Dispatchers.IO) {
                    locationDao.getHistoryLocations().collect { result ->
                        assertFalse(result.contains(fakeLocations[0]))
                        latch.countDown()
                    }
                }
            latch.await()
            job.cancelAndJoin()
        }

    @Test
    fun updateLocation_success() =
        runBlocking {
            locationDao.insert(fakeLocations[0])

            val updatedLocation =
                Location(id = 1, name = "Poznań", country = "Poland", url = "poznan-poland", 1725995923)
            locationDao.update(updatedLocation)

            val latch = CountDownLatch(1)
            val job =
                async(Dispatchers.IO) {
                    locationDao.getLocation(updatedLocation.id).collect { result ->
                        assertEquals(result.name, updatedLocation.name)
                        latch.countDown()
                    }
                }
            latch.await()
            job.cancelAndJoin()
        }

    @Test
    fun insertAllLocations_success() =
        runBlocking {
            locationDao.insertAll(fakeLocations)

            val latch = CountDownLatch(1)
            val job =
                async(Dispatchers.IO) {
                    locationDao.getHistoryLocations().collect { result ->
                        assertEquals(result.size, fakeLocations.size)
                        latch.countDown()
                    }
                }
            latch.await()
            job.cancelAndJoin()
        }

    @Test
    fun deleteAllLocations_success() =
        runBlocking {
            locationDao.insertAll(fakeLocations)
            locationDao.deleteAllLocations()

            val latch = CountDownLatch(1)
            val job =
                async(Dispatchers.IO) {
                    locationDao.getHistoryLocations().collect { result ->
                        assertEquals(result.size, 0)
                        latch.countDown()
                    }
                }
            latch.await()
            job.cancelAndJoin()
        }
}
