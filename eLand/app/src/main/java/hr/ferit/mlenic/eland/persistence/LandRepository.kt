package hr.ferit.mlenic.eland.persistence

import androidx.lifecycle.LiveData
import hr.ferit.mlenic.eland.model.Land

class LandRepository(private val landDao: LandDao) {

    val allLand: LiveData<List<Land>> = landDao.getAllLand()
    suspend fun insert(land: Land){
        landDao.insert(land)
    }

    suspend fun delete(land: Land){
        landDao.delete(land)
    }

    suspend fun update(land: Land){
        landDao.update(land)
    }
}