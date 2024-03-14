package dev.stukalo.repository.model

data class CloseApproachDataRepo(
    val closeApproachDate: String?,
    val closeApproachDateFull: String?,
    val epochDateCloseApproach: Long?,
    val missDistance: MissDistanceRepo?,
    val orbitingBody: String?,
    val relativeVelocity: RelativeVelocityRepo?,
)
