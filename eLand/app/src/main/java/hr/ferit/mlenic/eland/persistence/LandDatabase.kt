package hr.ferit.mlenic.eland.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hr.ferit.mlenic.eland.model.Land

@Database(entities = arrayOf(Land::class), version = 4, exportSchema = false)
abstract class LandDatabase: RoomDatabase() {
    abstract fun getLandDao(): LandDao

    companion object{
        @Volatile
        private var INSTANCE: LandDatabase? =null

        fun getDatabase(context: Context): LandDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LandDatabase::class.java,
                    "land_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE =instance
                instance
            }
        }
    }
}