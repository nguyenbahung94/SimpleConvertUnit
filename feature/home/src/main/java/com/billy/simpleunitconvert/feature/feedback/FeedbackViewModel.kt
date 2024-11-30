package com.billy.simpleunitconvert.feature.feedback

import androidx.compose.runtime.Stable
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(): BaseViewModel() {

    internal val uiState: ViewModelStateFlow<UiState<FeedbackState>> = viewModelStateFlow(
        UiState(isLoading = false, data = FeedbackState())
    )

    fun handleEvent(event: FeedbackSEvent) {
        when (event) {
            is FeedbackSEvent.SubmitEvent -> {
               if (checkNetwork()) {
                    submitFeedback(event.feedbackState)
                } else {

                }
            }
        }
    }

    private fun submitFeedback(feedbackState: FeedbackState) {
       // val db = Firebase.getInstance()
    }


    private fun checkNetwork(): Boolean {
        return true
    }


    companion object {
        private const val TAG = "FeedbackViewModel"
        const val COLLECTION_FEEDBACK = "SimpleUnitConvertFeedback"
    }
}

sealed interface FeedbackSEvent {
    data class SubmitEvent(val feedbackState: FeedbackState) : FeedbackSEvent
}

@Stable
data class FeedbackState(
    val ratingStart: Int = 0,
    val requestFeature: String = "",
    val experience: String = "",
)

