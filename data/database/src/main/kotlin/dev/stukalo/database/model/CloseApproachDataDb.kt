package dev.stukalo.database.model

import androidx.room.Embedded

data class CloseApproachDataDb(
    val closeApproachDate: String?,
    val closeApproachDateFull: String?,
    val epochDateCloseApproach: Long?,
    @Embedded val missDistance: MissDistanceDb?,
    val orbitingBody: String?,
    @Embedded val relativeVelocity: RelativeVelocityDb?,
)
