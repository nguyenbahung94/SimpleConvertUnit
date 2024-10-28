package com.billy.simpleunitconvert.feature.home

import com.billy.simpleunitconvert.core.model.HomeUnit
import com.billy.simpleunitconvert.core.model.UnitConvert
import kotlinx.collections.immutable.toImmutableList

object Utils {


    fun getCommon(): HomeUnit {
        return HomeUnit(
            "Favorite", listOf(
                UnitConvert("Currency", "Currency", "currency"),
                ).toImmutableList()
        )
    }

    fun getEngineering(): HomeUnit {
        return HomeUnit(
            "Engineering", listOf(
                UnitConvert("volume", "Volume", "volume"),
                UnitConvert("Area", "Area", "area"),
                UnitConvert("Energy", "Energy", "energy"),
                UnitConvert("Force", "Force", "force"),
                UnitConvert("Speed", "Speed", "speed"),
                UnitConvert("Fuel Consumption", "Fuel Consumption", "fuel_consumption"),
                UnitConvert("Data Storage", "Data Storage", "data_storage"),
                UnitConvert("Currency", "Currency", "currency"),
                UnitConvert("Acceleration", "Acceleration", "acceleration"),
                UnitConvert("Density", "Density", "density"),
                UnitConvert("Moment of Inertia", "Moment of Inertia", "moment_of_inertia"),
                UnitConvert("Torque", "Torque", "torque"),
                UnitConvert("Temperature", "Temperature", "temperature"),
                UnitConvert("Pressure", "Pressure", "pressure"),
                UnitConvert("Power", "Power", "power"),
                UnitConvert("Time", "Time", "time"),
                UnitConvert("Angle", "Angle", "angle"),
                UnitConvert("Numbers", "Numbers", "numbers"),
                UnitConvert("Volume - Dry", "Volume - Dry", "volume_dry"),
                UnitConvert("Velocity - Angular", "Velocity - Angular", "velocity_angular"),
                UnitConvert("Acceleration - Angular", "Acceleration - Angular", "acceleration_angular"),
                UnitConvert("Specific Volume", "Specific Volume", "specific_volume"),
                UnitConvert("Moment of Force", "Moment of Force", "moment_of_force"),
            ).toImmutableList()
        )
    }

    fun getHeat(): HomeUnit {
        return HomeUnit(
            "Heat", listOf(
                UnitConvert("Fuel Efficiency - Mass", "Fuel Efficiency - Mass", "fuel_efficiency_mass"),
                UnitConvert("Fuel Efficiency - Volume", "Fuel Efficiency - Volume", "fuel_efficiency_volume"),
                UnitConvert("Temperature Interval", "Temperature Interval", "temperature_interval"),
                UnitConvert("Thermal Expansion", "Thermal Expansion", "thermal_expansion"),
                UnitConvert("Thermal Resistance", "Thermal Resistance", "thermal_resistance"),
                UnitConvert("Thermal Conductivity", "Thermal Conductivity", "thermal_conductivity"),
                UnitConvert("Specific Heat Capacity", "Specific Heat Capacity", "specific_heat_capacity"),
                UnitConvert("Heat Density", "Heat Density", "heat_density"),
                UnitConvert("Heat Flux Density", "Heat Flux Density", "heat_flux_density"),
                UnitConvert("Heat Transfer Coefficient", "Heat Transfer Coefficient", "heat_transfer_coefficient"),
            ).toImmutableList()
        )
    }

    fun getFluids(): HomeUnit {
        return HomeUnit(
            "Fluids", listOf(
                UnitConvert("Flow", "Flow", "flow"),
                UnitConvert("Flow - Mass", "Flow - Mass", "flow_mass"),
                UnitConvert("Flow - Molar", "Flow - Molar", "flow_molar"),
                UnitConvert("Mass Flux Density", "Mass Flux Density", "mass_flux_density"),
                UnitConvert("Concentration - Molar", "Concentration - Molar", "concentration_molar"),
                UnitConvert("Concentration - Solution", "Concentration - Solution", "concentration_solution"),
                UnitConvert("Viscosity - Dynamic", "Viscosity - Dynamic", "viscosity_dynamic"),
                UnitConvert("Viscosity - Kinematic", "Viscosity - Kinematic", "viscosity_kinematic"),
                UnitConvert("Surface Tension", "Surface Tension", "surface_tension"),
                UnitConvert("Permeability", "Permeability", "permeability"),
            ).toImmutableList()
        )
    }

    fun getLight(): HomeUnit {
        return HomeUnit(
            "Light", listOf(
                UnitConvert("Luminance", "Luminace", "luminace"),
                UnitConvert("Luminous Intensity", "Luminous Intensity", "luminous_intensity"),
                UnitConvert("Illuminance", "Illuminance", "illuminance"),
                UnitConvert("Digital Image Resolution", "Digital Image Resolution", "digital_image_resolution"),
                UnitConvert("Frequency Wavelength", "Frequency Wavelength", "frequency_wavelength"),
            ).toImmutableList()
        )
    }

    fun getElectricity(): HomeUnit {
        return HomeUnit(
            "Electricity", listOf(
                UnitConvert("Charge", "Charge", "charge"),
                UnitConvert("Linear Charge Density", "Linear Charge Density", "linear_charge_density"),
                UnitConvert("Surface Charge Density", "Surface Charge Density", "surface_charge_density"),
                UnitConvert("Volume Charge Density", "Volume Charge Density", "volume_charge_density"),
                UnitConvert("Current", "Current", "current"),
                UnitConvert("Linear Current Density", "Linear Current Density", "linear_current_density"),
                UnitConvert("Surface Current Density", "Surface Current Density", "surface_current_density"),
                UnitConvert("Electric Field Strength", "Electric Field Strength", "electric_field_strength"),
                UnitConvert("Electric Potential", "Electric Potential", "electric_potential"),
                UnitConvert("Electric Resistance", "Electric Resistance", "electric_resistance"),
                UnitConvert("Electric Resistivity", "Electric Resistivity", "electric_resistivity"),
                UnitConvert("Electric Conductance", "Electric Conductance", "electric_conductance"),
                UnitConvert("Electric Conductivity", "Electric Conductivity", "electric_conductivity"),
                UnitConvert("Electrostatic Capacitance", "Electrostatic Capacitance", "electrostatic_capacitance"),
                UnitConvert("Inductance", "Inductance", "inductance"),
            ).toImmutableList()
        )
    }

    fun getMagnetic(): HomeUnit {
        return HomeUnit(
            "Magnetic", listOf(
                UnitConvert("Magneto Motive Force", "Magneto Motive Force", "magneto_motive_force"),
                UnitConvert("Magnetic Field Strength", "Magnetic Field Strength", "magnetic_field_strength"),
                UnitConvert("Magnetic Flux", "Magnetic Flux", "magnetic_flux"),
                UnitConvert("Magnetic Flux Density", "Magnetic Flux Density", "magnetic_flux_density"),
            ).toImmutableList()
        )
    }

    fun getRadiation(): HomeUnit {
        return HomeUnit(
            "Radiology", listOf(
                UnitConvert("Radiation", "Radiation", "radiation"),
                UnitConvert("Radiation-Activity", "Radiation-Activity", "radiation_activity"),
                UnitConvert("Radiation-Exposure", "Radiation-Exposure", "radiation_exposure"),
                UnitConvert("Radiation-Absorbed Dose", "Radiation-Absorbed Dose", "radiation_absorbed_dose"),
            ).toImmutableList()
        )
    }
}
