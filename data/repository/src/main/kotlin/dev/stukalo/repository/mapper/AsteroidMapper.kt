package dev.stukalo.repository.mapper

import dev.stukalo.network.model.Asteroid
import dev.stukalo.network.model.CloseApproachData
import dev.stukalo.network.model.EstimatedDiameter
import dev.stukalo.network.model.Feet
import dev.stukalo.network.model.Kilometers
import dev.stukalo.network.model.Meters
import dev.stukalo.network.model.Miles
import dev.stukalo.network.model.MissDistance
import dev.stukalo.network.model.RelativeVelocity
import dev.stukalo.repository.model.AsteroidRepo
import dev.stukalo.repository.model.CloseApproachDataRepo
import dev.stukalo.repository.model.EstimatedDiameterRepo
import dev.stukalo.repository.model.FeetRepo
import dev.stukalo.repository.model.KilometersRepo
import dev.stukalo.repository.model.MetersRepo
import dev.stukalo.repository.model.MilesRepo
import dev.stukalo.repository.model.MissDistanceRepo
import dev.stukalo.repository.model.RelativeVelocityRepo

fun Asteroid.mapToAsteroidRepo() = AsteroidRepo(
    absoluteMagnitudeH = absoluteMagnitudeH,
    closeApproachData = closeApproachData?.mapToCloseApproachDataRepo(),
    estimatedDiameter = estimatedDiameter?.mapToEstimatedDiameterRepo(),
    id = id,
    isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid,
    isSentryObject = isSentryObject,
    name = name,
    nasaJplUrl = nasaJplUrl,
    neoReferenceId = neoReferenceId,
)

fun List<CloseApproachData>.mapToCloseApproachDataRepo() : CloseApproachDataRepo{
    last().apply {
        return CloseApproachDataRepo(
            closeApproachDate = closeApproachDate,
            closeApproachDateFull = closeApproachDateFull,
            epochDateCloseApproach = epochDateCloseApproach,
            missDistance = missDistance?.mapToMissDistanceRepo(),
            orbitingBody = orbitingBody,
            relativeVelocity = relativeVelocity?.mapToRelativeVelocityRepo()
        )
    }
}

fun MissDistance.mapToMissDistanceRepo() = MissDistanceRepo(
    astronomical, kilometers, lunar, miles
)

fun RelativeVelocity.mapToRelativeVelocityRepo() = RelativeVelocityRepo(
    kilometersPerHour, kilometersPerSecond, milesPerHour
)

fun EstimatedDiameter.mapToEstimatedDiameterRepo() = EstimatedDiameterRepo(
    feet = feet?.mapToFeetRepo(),
    kilometers = kilometers?.mapToKilometersRepo(),
    meters = meters?.mapToMetersRepo(),
    miles = miles?.mapToMilesRepo()
)

fun Feet.mapToFeetRepo() = FeetRepo(
    estimatedDiameterMax, estimatedDiameterMin
)

fun Kilometers.mapToKilometersRepo() = KilometersRepo(
    estimatedDiameterMax, estimatedDiameterMin
)

fun Meters.mapToMetersRepo() = MetersRepo(
    estimatedDiameterMax, estimatedDiameterMin
)

fun Miles.mapToMilesRepo() = MilesRepo(
    estimatedDiameterMax, estimatedDiameterMin
)