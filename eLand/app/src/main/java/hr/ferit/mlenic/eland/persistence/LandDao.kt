package hr.ferit.mlenic.eland.persistence

import androidx.lifecycle.LiveData
import androidx.room.*
import hr.ferit.mlenic.eland.model.Land

@Dao
interface LandDao {
    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(land: Land)

    @Update
    suspend fun update(land: Land)

    @Delete
    suspend fun delete(land: Land)

    @Query("Select * from lands order by refNum ASC")
    fun getAllLand(): LiveData<List<Land>>
}


