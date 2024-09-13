package com.kmcoding.weather.fake

import com.kmcoding.weather.data.db.LocationDao
import com.kmcoding.weather.data.source.FakeDataSource.fakeLocations
import com.kmcoding.weather.domain.model.Location
import kotlinx.coroutines.flow.flow

class FakeLocationDaoImpl : LocationDao {
    override fun getHistoryLocations() =
        flow {
            emit(fakeLocations)
        }

    override fun getLocation(id: Int) = flow { emit(fakeLocations[0]) }

    override suspend fun insert(location: Location) {
    }

    override fun insertAll(locations: List<Location>) {
    }

    override suspend fun update(location: Location) {
    }

    override suspend fun delete(location: Location) {
    }

    override suspend fun deleteAllLocations() {
    }
}
