package hr.ferit.mlenic.eland.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import hr.ferit.mlenic.eland.model.Land
import hr.ferit.mlenic.eland.persistence.LandDatabase
import hr.ferit.mlenic.eland.persistence.LandRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LandViewModel(application: Application): AndroidViewModel(application) {
    val allLand: LiveData<List<Land>>
    val repository: LandRepository

    init {
        val dao = LandDatabase.getDatabase(application).getLandDao()
        repository = LandRepository(dao)
        allLand = repository.allLand
    }

    fun deleteLand(land: Land) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(land)
    }

    fun updateLand(land: Land) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(land)
    }

    fun addLand(land: Land) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(land)
    }
}