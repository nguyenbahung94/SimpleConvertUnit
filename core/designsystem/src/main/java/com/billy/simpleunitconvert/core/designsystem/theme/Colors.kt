package com.billy.simpleunitconvert.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.billy.simpleunitconvert.core.designsystem.R

@Immutable
data class Colors(
    val primary: Color,
    val background: Color,
    val backgroundLight: Color,
    val backgroundDark: Color,
    val absoluteWhite: Color,
    val absoluteBlack: Color,
    val white: Color,
    val white12: Color,
    val white56: Color,
    val white70: Color,
    val black: Color,
    val title: Color,
    val subtitle: Color,
    val backgroundCard: Color
) {

    companion object { /**
         * Provides the default colors for the light mode of the app.
         *
         */
        @Composable
        fun defaultDarkColors(): Colors = Colors(
            primary = colorResource(id = R.color.colorPrimary),
            background = colorResource(id = R.color.background_dark),
            backgroundLight = colorResource(id = R.color.background800_dark),
            backgroundDark = colorResource(id = R.color.background900_dark),
            absoluteWhite = colorResource(id = R.color.white),
            absoluteBlack = colorResource(id = R.color.black),
            white = colorResource(id = R.color.white_dark),
            white12 = colorResource(id = R.color.white_12_dark),
            white56 = colorResource(id = R.color.white_56_dark),
            white70 = colorResource(id = R.color.white_70_dark),
            black = colorResource(id = R.color.black_dark),
            title = colorResource(id = R.color.text_title_dark),
            subtitle = colorResource(id = R.color.text_sub_dark),
            backgroundCard = colorResource(id = R.color.background_card_dark)
        )
        /**
         * Provides the default colors for the light mode of the app.
         *
         */
        @Composable
        fun defaultLightColors(): Colors = Colors(
            primary = colorResource(id = R.color.colorPrimary),
            background = colorResource(id = R.color.background),
            backgroundLight = colorResource(id = R.color.background800),
            backgroundDark = colorResource(id = R.color.background900),
            absoluteWhite = colorResource(id = R.color.white),
            absoluteBlack = colorResource(id = R.color.black),
            white = colorResource(id = R.color.white),
            white12 = colorResource(id = R.color.white_12),
            white56 = colorResource(id = R.color.white_56),
            white70 = colorResource(id = R.color.white_70),
            black = colorResource(id = R.color.black),
            title = colorResource(id = R.color.text_title),
            subtitle = colorResource(id = R.color.text_sub),
            backgroundCard = colorResource(id = R.color.background_card_light)
        )
    }
}

