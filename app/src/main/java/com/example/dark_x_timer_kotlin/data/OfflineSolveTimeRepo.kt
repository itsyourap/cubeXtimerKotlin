package com.example.dark_x_timer_kotlin.data

import kotlinx.coroutines.flow.Flow

class OfflineSolveTimeRepo(private val dao: SolveTimeDao) : SolveTimeRepo {
    override suspend fun insert(item: SolveTimeItem) {
        dao.insert(item)
    }

    override suspend fun update(item: SolveTimeItem) {
        dao.update(item)
    }

    override suspend fun delete(item: SolveTimeItem) {
        dao.delete(item)
    }

    override fun getItem(id: Int): Flow<SolveTimeItem?> {
        return dao.getItem(id)
    }

    override fun getItems(): Flow<List<SolveTimeItem>> {
        return dao.getItems()
    }

}