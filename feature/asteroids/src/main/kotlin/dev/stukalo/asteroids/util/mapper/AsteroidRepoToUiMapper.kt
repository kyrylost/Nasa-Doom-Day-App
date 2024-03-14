package dev.stukalo.asteroids.util.mapper

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

fun AsteroidRepo.mapToAsteroidUi() =
    AsteroidUi(
        absoluteMagnitudeH = absoluteMagnitudeH,
        closeApproachData = closeApproachData?.mapToCloseApproachDataUi(),
        estimatedDiameter = estimatedDiameter?.mapToEstimatedDiameterUi(),
        id = id,
        isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid,
        isSentryObject = isSentryObject,
        name = name,
        nasaJplUrl = nasaJplUrl,
        neoReferenceId = neoReferenceId,
    )

fun CloseApproachDataRepo.mapToCloseApproachDataUi() =
    CloseApproachDataUi(
        closeApproachDate = closeApproachDate,
        closeApproachDateFull = closeApproachDateFull,
        epochDateCloseApproach = epochDateCloseApproach,
        missDistance = missDistance?.mapToMissDistanceUi(),
        orbitingBody = orbitingBody,
        relativeVelocity = relativeVelocity?.mapToRelativeVelocityUi(),
    )

fun MissDistanceRepo.mapToMissDistanceUi() =
    MissDistanceUi(
        astronomical,
        kilometers,
        lunar,
        miles,
    )

fun RelativeVelocityRepo.mapToRelativeVelocityUi() =
    RelativeVelocityUi(
        kilometersPerHour,
        kilometersPerSecond,
        milesPerHour,
    )

fun EstimatedDiameterRepo.mapToEstimatedDiameterUi() =
    EstimatedDiameterUi(
        feet = feet?.mapToFeetUi(),
        kilometers = kilometers?.mapToKilometersUi(),
        meters = meters?.mapToMetersUi(),
        miles = miles?.mapToMilesUi(),
    )

fun FeetRepo.mapToFeetUi() =
    FeetUi(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun KilometersRepo.mapToKilometersUi() =
    KilometersUi(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun MetersRepo.mapToMetersUi() =
    MetersUi(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun MilesRepo.mapToMilesUi() =
    MilesUi(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )
