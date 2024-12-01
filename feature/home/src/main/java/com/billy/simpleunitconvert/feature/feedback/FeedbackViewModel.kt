package com.billy.simpleunitconvert.feature.feedback

import android.util.Log
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateError
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateState
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateSuccess
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor() : BaseViewModel() {

    internal val uiState: ViewModelStateFlow<UiState<FeedbackState>> = viewModelStateFlow(
        UiState(isLoading = false, data = FeedbackState())
    )
    private var ratingStar: Int = 0
    private var requestFeature: String = ""
    private var experience: String = ""

    fun handleEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.SubmitEvent -> {
               submitFeedback()
            }

            is FeedbackEvent.RatingEvent -> {
                ratingStar = event.ratingStart
                Log.e(TAG, "RatingEvent: $ratingStar")
            }

            is FeedbackEvent.FeatureRequestEvent -> {
                requestFeature = event.requestFeature
                Log.e(TAG, "FeatureRequestEvent: $requestFeature")
            }

            is FeedbackEvent.AppExperienceEvent -> {
                experience = event.experience
                Log.e(TAG, "AppExperienceEvent: $experience")
            }

        }
    }

    private fun submitFeedback() {
       if (ratingStar == 0 && requestFeature.isEmpty() && experience.isEmpty()) {
            uiState.updateError( "Please fill all fields, thank you! \uD83D\uDE4F\uD83C\uDF3C")
            return
        }

        // Submit feedback to Firestore
        // a sum it's successful
        uiState.updateSuccess(success = true)

    }

    companion object {
        private const val TAG = "FeedbackViewModel"
        const val COLLECTION_FEEDBACK = "SimpleUnitConvertFeedback"
    }
}

sealed interface FeedbackEvent {
    data object SubmitEvent : FeedbackEvent
    data class RatingEvent(val ratingStart: Int) : FeedbackEvent
    data class FeatureRequestEvent(val requestFeature: String) : FeedbackEvent
    data class AppExperienceEvent(val experience: String) : FeedbackEvent
}

data class FeedbackState(
   val state: String = "",
)

