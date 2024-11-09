package com.billy.simpleunitconvert.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class HomeUnitEntity(
    @PrimaryKey val shortName: String,
    val groupName: String,
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
    @PrimaryKey val category: String,
    val homeGroup: String,
    val name: String,
    val shortName: String,
    val image: String,
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
    val conversion: Double?,
    val scale: Double?,
    val offset: Double?,
    val popular: Boolean
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

