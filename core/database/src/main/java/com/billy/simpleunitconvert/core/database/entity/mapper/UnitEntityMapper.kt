package com.billy.simpleunitconvert.core.database.entity.mapper

import com.billy.simpleunitconvert.core.database.entity.UnitConvertEntity
import com.billy.simpleunitconvert.core.model.UnitConvertData

object UnitEntityMapper : EntityMapper<List<UnitConvertData>, List<UnitConvertEntity>> {

    override fun asEntity(domain: List<UnitConvertData>): List<UnitConvertEntity> {
        return domain.map { unitConvertData ->
            with(unitConvertData) {
                UnitConvertEntity(
                    category = category,
                    homeGroup = homeGroup,
                    name = name,
                    shortName = shortName,
                    image = image,
                    isFavorite = isFavorite

                )
            }
        }
    }

    override fun asDomain(entity: List<UnitConvertEntity>): List<UnitConvertData> {
        return entity.map { unitConvertEntity ->
            with(unitConvertEntity) {
                UnitConvertData(
                    category = category,
                    homeGroup = homeGroup,
                    name = name,
                    shortName = shortName,
                    image = image,
                    isFavorite = isFavorite
                )
            }
        }
    }
}

fun List<UnitConvertData>.asEntity(): List<UnitConvertEntity> {
    return UnitEntityMapper.asEntity(this)
}

fun List<UnitConvertEntity>.asDomain(): List<UnitConvertData> {
    return UnitEntityMapper.asDomain(this)
}
