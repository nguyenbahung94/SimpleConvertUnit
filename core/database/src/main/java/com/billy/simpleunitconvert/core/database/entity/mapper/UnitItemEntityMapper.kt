package com.billy.simpleunitconvert.core.database.entity.mapper

import com.billy.simpleunitconvert.core.database.entity.UnitItemEntity
import com.billy.simpleunitconvert.core.model.UnitItemData

object UnitItemEntityMapper : EntityMapper<List<UnitItemData>, List<UnitItemEntity>> {

    override fun asEntity(domain: List<UnitItemData>): List<UnitItemEntity> {
        return domain.map { unitItemData ->
            with(unitItemData) {
                UnitItemEntity(
                    unitCategory = category,
                    unitName = name,
                    symbol = symbol,
                    conversion = conversionFactor,
                    scale = scaleFactor,
                    offset = offset,
                    popular = popular
                )
            }
        }
    }

    override fun asDomain(entity: List<UnitItemEntity>): List<UnitItemData> {
        return entity.map { unitItemEntity ->
            with(unitItemEntity) {
                UnitItemData(
                    symbol = symbol,
                    name = unitName,
                    conversionFactor = conversion,
                    scaleFactor = scale,
                    offset = offset,
                    category = unitCategory,
                    popular = popular
                )
            }
        }
    }
}

fun List<UnitItemData>.asEntity(): List<UnitItemEntity> {
    return UnitItemEntityMapper.asEntity(this)
}

fun List<UnitItemEntity>.asDomain(): List<UnitItemData> {
    return UnitItemEntityMapper.asDomain(this)
}
