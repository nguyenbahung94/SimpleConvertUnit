package com.billy.simpleunitconvert.core.database.entity.mapper

import com.billy.simpleunitconvert.core.database.entity.UnitEntity
import com.billy.simpleunitconvert.core.model.UnitItem

object UnitEntityMapper : EntityMapper<List<UnitItem>, List<UnitEntity>> {

    override fun asEntity(domain: List<UnitItem>): List<UnitEntity> {
        return domain.map { unit ->
            UnitEntity(
                symbol = unit.symbol,
                name = unit.name,
                conversionFactorToMeter = unit.conversionFactorToMeter,
                category = unit.category
            )
        }
    }

    override fun asDomain(entity: List<UnitEntity>): List<UnitItem> {
        return entity.map { unitEntity ->
            UnitItem(
                symbol = unitEntity.symbol,
                name = unitEntity.name,
                conversionFactorToMeter = unitEntity.conversionFactorToMeter,
                category = unitEntity.category
            )
        }
    }
}

fun List<UnitItem>.asEntity(): List<UnitEntity> {
    return UnitEntityMapper.asEntity(this)
}

fun List<UnitEntity>?.asDomain(): List<UnitItem> {
    return UnitEntityMapper.asDomain(this.orEmpty())
}
