package hr.ferit.mlenic.eland.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.Serializable
import java.text.DateFormat

@Entity(tableName="lands")
data class Land(
    @ColumnInfo(name="refNum") val refNum: Int,
    @ColumnInfo(name="name") val landName: String,
    @ColumnInfo(name="timestamp") val timeStamp: String,
    @ColumnInfo(name="culture") val landCulture: String,
    @ColumnInfo(name="area") val landArea: Double,
    @ColumnInfo(name="latitude") val landLatitude: Double,
    @ColumnInfo(name="longitude") val landLongitude: Double,
    @ColumnInfo(name="comment") val landComment:String) {
        @PrimaryKey(autoGenerate = true) var id = 0
    }

