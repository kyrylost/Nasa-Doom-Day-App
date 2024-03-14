package dev.stukalo.common.model

data class CloseApproachDataUi(
    val closeApproachDate: String?,
    val closeApproachDateFull: String?,
    val epochDateCloseApproach: Long?,
    val missDistance: MissDistanceUi?,
    val orbitingBody: String?,
    val relativeVelocity: RelativeVelocityUi?,
)
