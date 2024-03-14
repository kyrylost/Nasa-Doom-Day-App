package dev.stukalo.asteroiddetails.util

import dev.stukalo.common.model.AsteroidUi
import dev.stukalo.common.model.CloseApproachDataUi
import dev.stukalo.common.model.EstimatedDiameterUi
import dev.stukalo.common.model.FeetUi
import dev.stukalo.common.model.KilometersUi
import dev.stukalo.common.model.MetersUi
import dev.stukalo.common.model.MilesUi
import dev.stukalo.common.model.MissDistanceUi
import dev.stukalo.common.model.RelativeVelocityUi
import dev.stukalo.repository.model.AsteroidRepo
import dev.stukalo.repository.model.CloseApproachDataRepo
import dev.stukalo.repository.model.EstimatedDiameterRepo
import dev.stukalo.repository.model.FeetRepo
import dev.stukalo.repository.model.KilometersRepo
import dev.stukalo.repository.model.MetersRepo
import dev.stukalo.repository.model.MilesRepo
import dev.stukalo.repository.model.MissDistanceRepo
import dev.stukalo.repository.model.RelativeVelocityRepo

fun AsteroidUi.mapToAsteroidRepo() =
    id?.let {
        AsteroidRepo(
            absoluteMagnitudeH = absoluteMagnitudeH,
            closeApproachData = closeApproachData?.mapToCloseApproachDataRepo(),
            estimatedDiameter = estimatedDiameter?.mapToEstimatedDiameterRepo(),
            id = it,
            isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid,
            isSentryObject = isSentryObject,
            name = name,
            nasaJplUrl = nasaJplUrl,
            neoReferenceId = neoReferenceId,
        )
    }

fun CloseApproachDataUi.mapToCloseApproachDataRepo() =
    CloseApproachDataRepo(
        closeApproachDate = closeApproachDate,
        closeApproachDateFull = closeApproachDateFull,
        epochDateCloseApproach = epochDateCloseApproach,
        missDistance = missDistance?.mapToMissDistanceRepo(),
        orbitingBody = orbitingBody,
        relativeVelocity = relativeVelocity?.mapToRelativeVelocityRepo(),
    )

fun MissDistanceUi.mapToMissDistanceRepo() =
    MissDistanceRepo(
        astronomical,
        kilometers,
        lunar,
        miles,
    )

fun RelativeVelocityUi.mapToRelativeVelocityRepo() =
    RelativeVelocityRepo(
        kilometersPerHour,
        kilometersPerSecond,
        milesPerHour,
    )

fun EstimatedDiameterUi.mapToEstimatedDiameterRepo() =
    EstimatedDiameterRepo(
        feet = feet?.mapToFeetRepo(),
        kilometers = kilometers?.mapToKilometersRepo(),
        meters = meters?.mapToMetersRepo(),
        miles = miles?.mapToMilesRepo(),
    )

fun FeetUi.mapToFeetRepo() =
    FeetRepo(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun KilometersUi.mapToKilometersRepo() =
    KilometersRepo(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun MetersUi.mapToMetersRepo() =
    MetersRepo(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun MilesUi.mapToMilesRepo() =
    MilesRepo(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )
