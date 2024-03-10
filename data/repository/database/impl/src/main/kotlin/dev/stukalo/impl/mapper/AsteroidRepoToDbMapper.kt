package dev.stukalo.impl.mapper

import dev.stukalo.database.model.AsteroidDb
import dev.stukalo.database.model.CloseApproachDataDb
import dev.stukalo.database.model.EstimatedDiameterDb
import dev.stukalo.database.model.FeetDb
import dev.stukalo.database.model.KilometersDb
import dev.stukalo.database.model.MetersDb
import dev.stukalo.database.model.MilesDb
import dev.stukalo.database.model.MissDistanceDb
import dev.stukalo.database.model.RelativeVelocityDb
import dev.stukalo.repository.model.AsteroidRepo
import dev.stukalo.repository.model.CloseApproachDataRepo
import dev.stukalo.repository.model.EstimatedDiameterRepo
import dev.stukalo.repository.model.FeetRepo
import dev.stukalo.repository.model.KilometersRepo
import dev.stukalo.repository.model.MetersRepo
import dev.stukalo.repository.model.MilesRepo
import dev.stukalo.repository.model.MissDistanceRepo
import dev.stukalo.repository.model.RelativeVelocityRepo

fun AsteroidRepo.mapToAsteroidDb() =
    id?.let {
        AsteroidDb(
            absoluteMagnitudeH = absoluteMagnitudeH,
            closeApproachData = closeApproachData?.mapToCloseApproachDataDb(),
            estimatedDiameter = estimatedDiameter?.mapToEstimatedDiameterDb(),
            id = it,
            isPotentiallyHazardousAsteroid = isPotentiallyHazardousAsteroid,
            isSentryObject = isSentryObject,
            name = name,
            nasaJplUrl = nasaJplUrl,
            neoReferenceId = neoReferenceId,
        )
    }

fun CloseApproachDataRepo.mapToCloseApproachDataDb() =
    CloseApproachDataDb(
        closeApproachDate = closeApproachDate,
        closeApproachDateFull = closeApproachDateFull,
        epochDateCloseApproach = epochDateCloseApproach,
        missDistance = missDistance?.mapToMissDistanceDb(),
        orbitingBody = orbitingBody,
        relativeVelocity = relativeVelocity?.mapToRelativeVelocityDb(),
    )

fun MissDistanceRepo.mapToMissDistanceDb() =
    MissDistanceDb(
        astronomical,
        kilometers,
        lunar,
        miles,
    )

fun RelativeVelocityRepo.mapToRelativeVelocityDb() =
    RelativeVelocityDb(
        kilometersPerHour,
        kilometersPerSecond,
        milesPerHour,
    )

fun EstimatedDiameterRepo.mapToEstimatedDiameterDb() =
    EstimatedDiameterDb(
        feet = feet?.mapToFeetDb(),
        kilometers = kilometers?.mapToKilometersDb(),
        meters = meters?.mapToMetersDb(),
        miles = miles?.mapToMilesDb(),
    )

fun FeetRepo.mapToFeetDb() =
    FeetDb(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun KilometersRepo.mapToKilometersDb() =
    KilometersDb(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun MetersRepo.mapToMetersDb() =
    MetersDb(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )

fun MilesRepo.mapToMilesDb() =
    MilesDb(
        estimatedDiameterMax,
        estimatedDiameterMin,
    )
