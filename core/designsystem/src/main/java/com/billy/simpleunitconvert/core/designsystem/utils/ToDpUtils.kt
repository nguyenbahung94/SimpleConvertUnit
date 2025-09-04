package com.billy.simpleunitconvert.core.designsystem.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.billy.simpleunitconvert.core.designsystem.R

@Composable
fun Int.pxToDp(): Dp = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun String.getResIdByName(): Int {
    val nameImage = this.substringBefore(".")
    return when (nameImage) {
        "volume" -> R.drawable.volume
        "area" -> R.drawable.area
        "energy" -> R.drawable.energy
        "force" -> R.drawable.force
        "speed" -> R.drawable.speed
        "fuel_consumption" -> R.drawable.fuel_consumption
        "data_storage" -> R.drawable.data_storage
        "currency" -> R.drawable.currency
        "acceleration" -> R.drawable.acceleration
        "density" -> R.drawable.density
        "moment_of_inertia" -> R.drawable.moment_of_inertia
        "torque" -> R.drawable.torque
        "temperature" -> R.drawable.temperature
        "pressure" -> R.drawable.pressure
        "power" -> R.drawable.power
        "time" -> R.drawable.time
        "angle" -> R.drawable.angle
        "numbers" -> R.drawable.numbers
        "volume_dry" -> R.drawable.volume_dry
        "velocity_angular" -> R.drawable.velocity_angular
        "acceleration_angular" -> R.drawable.acceleration_angular
        "specific_volume" -> R.drawable.specific_volume
        "moment_of_force" -> R.drawable.moment_of_force
        "fuel_efficiency_mass" -> R.drawable.fuel_efficiency_mass
        "fuel_efficiency_volume" -> R.drawable.fuel_efficiency_volume
        "temperature_interval" -> R.drawable.temperature_interval
        "thermal_expansion" -> R.drawable.thermal_expansion
        "thermal_resistance" -> R.drawable.thermal_resistance
        "thermal_conductivity" -> R.drawable.thermal_conductivity
        "specific_heat_capacity" -> R.drawable.specific_heat_capacity
        "heat_density" -> R.drawable.heat_density
        "heat_flux_density" -> R.drawable.heat_flux_density
        "heat_transfer_coefficient" -> R.drawable.heat_transfer_coefficient
        "flow" -> R.drawable.flow
        "flow_mass" -> R.drawable.flow_mass
        "flow_molar" -> R.drawable.flow_molar
        "mass_flux_density" -> R.drawable.mass_flux_density
        "concentration_molar" -> R.drawable.concentration_molar
        "concentration_solution" -> R.drawable.concentration_solution
        "viscosity_dynamic" -> R.drawable.viscosity_dynamic
        "viscosity_kinematic" -> R.drawable.viscosity_kinematic
        "surface_tension" -> R.drawable.surface_tension
        "permeability" -> R.drawable.permeability
        "luminance" -> R.drawable.luminance
        "luminous_intensity" -> R.drawable.luminous_intensity
        "illuminance" -> R.drawable.illumination
        "digital_image_resolution" -> R.drawable.digital_image_resolution
        "frequency_wavelength" -> R.drawable.frequency_wavelength
        "charge" -> R.drawable.charge
        "linear_charge_density" -> R.drawable.linear_charge_density
        "surface_charge_density" -> R.drawable.surface_charge_density
        "volume_charge_density" -> R.drawable.volume_charge_density
        "current" -> R.drawable.current
        "linear_current_density" -> R.drawable.linear_current_density
        "surface_current_density" -> R.drawable.surface_current_density
        "electric_field_strength" -> R.drawable.electric_field_strength
        "electric_potential" -> R.drawable.electric_potential
        "electric_resistance" -> R.drawable.electric_resistance
        "electric_resistivity" -> R.drawable.electric_resistivity
        "electric_conductance" -> R.drawable.electric_conductance
        "electric_conductivity" -> R.drawable.electric_conductivity
        "electrostatic_capacitance" -> R.drawable.electrostatic_capacitance
        "inductance" -> R.drawable.inductance
        "magneto_motive_force" -> R.drawable.magneto_motive_force
        "magnetic_field_strength" -> R.drawable.magnetic_field_strength
        "magnetic_flux" -> R.drawable.magnetic_flux
        "magnetic_flux_density" -> R.drawable.magnetic_flux_density
        "radiation" -> R.drawable.radiation
        "radiation_activity" -> R.drawable.radiation_activity
        "radiation_exposure" -> R.drawable.radiation_exposure
        "radiation_absorbed_dose" -> R.drawable.radiation_absorbed_dose
        "length" -> R.drawable.length
        "weight_mass" -> R.drawable.weight_mass
        else -> R.drawable.icon_swap
    }
}
