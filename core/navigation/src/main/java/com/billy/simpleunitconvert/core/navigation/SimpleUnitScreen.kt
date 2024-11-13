package com.billy.simpleunitconvert.core.navigation

import kotlinx.serialization.Serializable

sealed class SimpleUnitScreen {
    @Serializable
    data object Home : SimpleUnitScreen()
    @Serializable
    data object Search : SimpleUnitScreen()
    @Serializable
    data object Calculator : SimpleUnitScreen()
    @Serializable
    data object Settings : SimpleUnitScreen()
}
