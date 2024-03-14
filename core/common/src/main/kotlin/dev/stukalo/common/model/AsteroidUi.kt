package dev.stukalo.common.model

data class AsteroidUi(
    val absoluteMagnitudeH: Double?,
    val closeApproachData: CloseApproachDataUi?,
    val estimatedDiameter: EstimatedDiameterUi?,
    val id: String?,
    val isPotentiallyHazardousAsteroid: Boolean?,
    val isSentryObject: Boolean?,
    val name: String?,
    val nasaJplUrl: String?,
    val neoReferenceId: String?,
)
