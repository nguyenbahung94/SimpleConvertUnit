package com.billy.simpleunitconvert.core.database.entity.mapper

import com.billy.simpleunitconvert.core.database.entity.HomeUnitEntity
import com.billy.simpleunitconvert.core.model.home.HomeUnitData

object HomeUnitEntityMapper : EntityMapper<List<HomeUnitData>, List<HomeUnitEntity>> {

    override fun asEntity(domain: List<HomeUnitData>): List<HomeUnitEntity> {
        return domain.map { homeUnit ->
            with(homeUnit) {
                HomeUnitEntity(
                    groupName = groupName, shortName = shortName
                )
            }
        }
    }

    override fun asDomain(entity: List<HomeUnitEntity>): List<HomeUnitData> {
        return entity.map { homeUnitEntity ->
            with(homeUnitEntity) {
                HomeUnitData(
                    groupName = groupName, shortName = shortName
                )
            }
        }
    }
}

fun List<HomeUnitData>.asEntity(): List<HomeUnitEntity> {
    return HomeUnitEntityMapper.asEntity(this)
}

fun List<HomeUnitEntity>.asDomain(): List<HomeUnitData> {
    return HomeUnitEntityMapper.asDomain(this)
}
