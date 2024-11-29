package com.billy.simpleunitconvert.core.data.repository.init

import android.content.Context
import android.util.Log
import com.billy.simpleunitconvert.core.database.UnitDao
import com.billy.simpleunitconvert.core.database.entity.mapper.asEntity
import com.billy.simpleunitconvert.core.model.home.HomeUnitData
import com.billy.simpleunitconvert.core.model.home.UnitConvertData
import com.billy.simpleunitconvert.core.model.home.UnitItemData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.singleOrNull
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject

internal class CreateDatabaseRepositoryImpl @Inject constructor(
    private val unitDao: UnitDao,
    @ApplicationContext private val context: Context,
    private val json: Json,
) : CreateDatabaseRepository {

    override suspend fun readDataSaveToDatabase() {
        withContext(Dispatchers.IO) {
            unitDao.getHomeUnitList().collect{
                if (it.isEmpty()) {
                    insertAllData()
                }
            }
        }
    }

    private suspend fun insertAllData() {
        Log.e("insertAllData", "insertAllData")
        insertHomeUnits()
        insertUnitConverter()
        insertUnitItems()
    }

    private suspend fun insertUnitItems() {
        val TAG = "insertUnitItems == "
        val listItems = listOf(
            "volume.json",
            "temperature.json",
            "area.json",
            "pressure.json",
            "energy.json",
            "power.json",
            "force.json",
            "time.json",
            "speed.json",
            "angle.json",
            "fuel_consumption.json",
            "numbers.json",
            "data_storage.json",
            "volume_dry.json",
            "currency.json",
            "velocity_angular.json",
            "acceleration.json",
            "acceleration_angular.json",
            "density.json",
            "specific_volume.json",
            "moment_of_inertia.json",
            "moment_of_force.json",
            "torque.json",
            "fuel_efficiency_mass.json",
            "fuel_efficiency_volume.json",
            "temperature_interval.json",
            "thermal_expansion.json",
            "thermal_resistance.json",
            "thermal_conductivity.json",
            "specific_heat_capacity.json",
            "heat_density.json",
            "heat_flux_density.json",
            "heat_transfer_coefficient.json",
            "flow.json",
            "flow_mass.json",
            "flow_molar.json",
            "mass_flux_density.json",
            "concentration_molar.json",
            "concentration_solution.json",
            "viscosity_dynamic.json",
            "viscosity_kinematic.json",
            "surface_tension.json",
            "permeability.json",
            "luminance.json",
            "luminous_intensity.json",
            "illumination.json",
            "digital_image_resolution.json",
            "frequency_wavelength.json",
            "charge.json",
            "linear_charge_density.json",
            "surface_charge_density.json",
            "volume_charge_density.json",
            "current.json",
            "linear_current_density.json",
            "surface_current_density.json",
            "electric_field_strength.json",
            "electric_potential.json",
            "electric_resistance.json",
            "electric_resistivity.json",
            "electric_conductance.json",
            "electric_conductivity.json",
            "electrostatic_capacitance.json",
            "inductance.json",
            "magneto_motive_force.json",
            "magnetic_field_strength.json",
            "magnetic_flux.json",
            "magnetic_flux_density.json",
            "radiation.json",
            "radiation_activity.json",
            "radiation_exposure.json",
            "radiation_absorbed_dose.json"
        )

        listItems.forEach { item ->
            runCatching {
                val jsonString = context.assets.open(item).bufferedReader().use { it.readText() }

                val unitConverts: List<UnitItemData> = json.decodeFromString(jsonString)

                Log.i(TAG, "item name == : $item")
                Log.i(TAG, "allUnits: $unitConverts")

                unitDao.insertUnitItemEntities(unitConverts.asEntity())

            }.onFailure {
                Log.e(TAG, "error at $item vs  ${it.message}")
            }.onSuccess {
                Log.i(TAG, "success with $item")
            }
        }
    }

    private suspend fun insertUnitConverter() {
        val TAG = "insertUnitConverter == "

        runCatching {
            val jsonString =
                context.assets.open("UnitConvertEntity.json").bufferedReader().use { it.readText() }

            val unitConverts: List<UnitConvertData> = json.decodeFromString(jsonString)

            Log.i(TAG, "allUnits: $unitConverts")

            unitDao.insertUnitConverts(unitConverts.asEntity())

        }.onFailure {
            Log.e(TAG, "error: ${it.message}")
        }.onSuccess {
            Log.i(TAG, "success")
        }
    }

    private suspend fun insertHomeUnits() {
        val TAG = "HomeUnit == "

        runCatching {
            val jsonString =
                context.assets.open("HomeUnitEntity.json").bufferedReader().use { it.readText() }

            val homeUnits: List<HomeUnitData> = json.decodeFromString(jsonString)

            Log.e(TAG, "allUnits: $homeUnits")

            unitDao.insertHomeUnits(homeUnits.asEntity())

        }.onFailure {
            Log.e(TAG, "error: ${it.message}")
        }.onSuccess {
            Log.i(TAG, "success")
        }
    }

}
