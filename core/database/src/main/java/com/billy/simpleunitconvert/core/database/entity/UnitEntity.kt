package com.billy.simpleunitconvert.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class UnitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val symbol: String,
    val name: String,
    val conversionFactorToMeter: Double,
    val category: String
)

@Entity
data class HomeUnitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val groupName: String,
    val shortName: String,
)

@Entity(
    foreignKeys = [ForeignKey(
        entity = HomeUnitEntity::class,
        parentColumns = ["shortName"],
        childColumns = ["homeGroup"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class UnitConvertEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val homeGroup: String,
    val name: String,
    val shortName: String,
    val image: String,
    val category: String,
    val isFavorite: Boolean
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UnitConvertEntity::class,
            parentColumns = ["category"],
            childColumns = ["unitCategory"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class UnitItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val unitCategory: String,
    val unitName: String,
    val symbol: String,
    val conversion: Double
)

data class HomeUnitWithUnitConvert(
    @Embedded val homeUnit: HomeUnitEntity,
    @Relation(
        parentColumn = "shortName",
        entityColumn = "homeGroup"
    )
    val unitConverts: List<UnitConvertEntity>
)

data class UnitConvertWithUnitItem(
    @Embedded val unitConvert: UnitConvertEntity,
    @Relation(
        parentColumn = "category",
        entityColumn = "unitCategory"
    )
    val unitItems: List<UnitItemEntity>
)

