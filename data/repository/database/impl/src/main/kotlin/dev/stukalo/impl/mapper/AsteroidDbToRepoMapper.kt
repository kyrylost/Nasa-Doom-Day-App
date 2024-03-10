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

fun AsteroidDb.mapToAsteroidRepo() =
    AsteroidRepo(
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

fun CloseApproachDataDb.mapToCloseApproachDataRepo() =  CloseApproachDataRepo(
    closeApproachDate = closeApproachDate,
    closeApproachDateFull = closeApproachDateFull,
    epochDateCloseApproach = epochDateCloseApproach,
    missDistance = missDistance?.mapToMissDistanceRepo(),
    orbitingBody = orbitingBody,
    relativeVelocity = relativeVelocity?.mapToRelativeVelocityRepo(),
)

fun MissDistanceDb.mapToMissDistanceRepo() =
    MissDistanceRepo(
        astronomical,
        kilometers,
        lunar,
        miles,
    )

fun RelativeVelocityDb.mapToRelativeVelocityRepo() =
    RelativeVelocityRepo(
        kilometersPerHour,
        kilometersPerSecond,
        milesPerHour,
    )

fun EstimatedDiameterDb.mapToEstimatedDiameterRepo() =
    EstimatedDiameterRepo(
        feet = feet?.mapToFeetRepo(),
        kilometers = kilometers?.mapToKilometersRepo(),
        meters = meters?.mapToMetersRepo(),
        miles = miles?.mapToMilesRepo(),
    )

fun FeetDb.mapToFeetRepo() =
    FeetRepo(
        feetEstimatedDiameterMax,
        feetEstimatedDiameterMin,
    )

fun KilometersDb.mapToKilometersRepo() =
    KilometersRepo(
        kilometerEstimatedDiameterMax,
        kilometerEstimatedDiameterMin,
    )

fun MetersDb.mapToMetersRepo() =
    MetersRepo(
        meterEstimatedDiameterMax,
        meterEstimatedDiameterMin,
    )

fun MilesDb.mapToMilesRepo() =
    MilesRepo(
        mileEstimatedDiameterMax,
        mileEstimatedDiameterMin,
    )
