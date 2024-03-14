package dev.stukalo.repository.model

data class AsteroidRepo(
    val absoluteMagnitudeH: Double?,
    val closeApproachData: CloseApproachDataRepo?,
    val estimatedDiameter: EstimatedDiameterRepo?,
    val id: String?,
    val isPotentiallyHazardousAsteroid: Boolean?,
    val isSentryObject: Boolean?,
    val isShownToUser: Boolean = false,
    val name: String?,
    val nasaJplUrl: String?,
    val neoReferenceId: String?,
)
