package dev.stukalo.database.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroids")
data class AsteroidDb(
    @PrimaryKey(autoGenerate = false) val id: String,
    val absoluteMagnitudeH: Double?,
    @Embedded val closeApproachData: CloseApproachDataDb?,
    @Embedded val estimatedDiameter: EstimatedDiameterDb?,
    val isPotentiallyHazardousAsteroid: Boolean?,
    val isSentryObject: Boolean?,
    val isShownToUser: Boolean = false,
    val name: String?,
    val nasaJplUrl: String?,
    val neoReferenceId: String?,
)
