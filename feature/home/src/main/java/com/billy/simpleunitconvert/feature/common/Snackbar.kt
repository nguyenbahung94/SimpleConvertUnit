package com.billy.simpleunitconvert.feature.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Custom Snackbar Visuals with color variations
 */
data class CustomSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    val type: SnackbarType = SnackbarType.INFO,
    override val duration: SnackbarDuration
) : SnackbarVisuals

/**
 * Snackbar Type Enum
 */
enum class SnackbarType {
    SUCCESS, ERROR, WARNING, INFO
}

/**
 * Custom Snackbar Style Based on Type
 */
@Composable
fun CustomSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier
) {
    val customVisuals = snackbarData.visuals as? CustomSnackbarVisuals

    val backgroundColor = when (customVisuals?.type) {
        SnackbarType.SUCCESS -> Color(0xFFA5D6A7)
        SnackbarType.ERROR -> Color(0xFFE57373)
        SnackbarType.WARNING -> Color(0xFFFFB74D)
        SnackbarType.INFO -> Color(0xFF90CAF9)
        null -> SnackbarDefaults.color
    }

    Snackbar(
        modifier = modifier.padding(12.dp),
        content = {
            Text(
                text = snackbarData.visuals.message,
                color = Color.White
            )
        },
        action = {
            snackbarData.visuals.actionLabel?.let { label ->
                TextButton(
                    onClick = { snackbarData.performAction() }
                ) {
                    Text(
                        text = label,
                        color = Color.White
                    )
                }
            }
        },
        containerColor = backgroundColor
    )
}

/**
 * Snackbar Extension Functions
 */
suspend fun SnackbarHostState.showCustomSnackbar(
    message: String,
    type: SnackbarType = SnackbarType.INFO,
    actionLabel: String? = null,
    actionCallback: (() -> Unit)? = null,
    duration: SnackbarDuration = SnackbarDuration.Short
) {
    showSnackbar(
        CustomSnackbarVisuals(
            message = message,
            actionLabel = actionLabel,
            type = type,
            duration = duration
        )
    )
    actionCallback?.invoke()
}
