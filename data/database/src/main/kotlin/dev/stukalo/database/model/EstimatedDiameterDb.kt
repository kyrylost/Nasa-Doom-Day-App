package dev.stukalo.database.model

import androidx.room.Embedded

data class EstimatedDiameterDb(
    @Embedded val feet: FeetDb?,
    @Embedded val kilometers: KilometersDb?,
    @Embedded val meters: MetersDb?,
    @Embedded val miles: MilesDb?,
)
