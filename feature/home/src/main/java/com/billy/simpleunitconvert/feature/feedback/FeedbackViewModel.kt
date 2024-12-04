package com.billy.simpleunitconvert.feature.feedback

import android.provider.MediaStore.MediaColumns.DOCUMENT_ID
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.billy.simpleunitconvert.core.data.repository.query.QueryDataBaseRepository
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateError
import com.billy.simpleunitconvert.core.viewmodel.StateExtensions.updateSuccess
import com.billy.simpleunitconvert.core.viewmodel.UiState
import com.billy.simpleunitconvert.core.viewmodel.ViewModelStateFlow
import com.billy.simpleunitconvert.feature.common.Utils
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedbackViewModel @Inject constructor(
    private val queryDataBaseRepository: QueryDataBaseRepository,
) : BaseViewModel() {

    internal val uiState: ViewModelStateFlow<UiState<FeedbackState>> = viewModelStateFlow(
        UiState(isLoading = false, data = FeedbackState())
    )
    private var ratingStar: Int = 0
    private var requestFeature: String = ""
    private var experience: String = ""
    private var feedbackCollection = FirebaseFirestore.getInstance()
        .collection(COLLECTION_FEEDBACK)
    private lateinit var uid: String
    init {
        viewModelScope.launch {
            queryDataBaseRepository.getInformation().collect {
                it?.let { uid = it }
            }
        }
    }

    fun handleEvent(event: FeedbackEvent) {
        when (event) {
            is FeedbackEvent.SubmitEvent -> {
                submitFeedback()
            }

            is FeedbackEvent.RatingEvent -> {
                ratingStar = event.ratingStart
            }

            is FeedbackEvent.FeatureRequestEvent -> {
                requestFeature = event.requestFeature
            }

            is FeedbackEvent.AppExperienceEvent -> {
                experience = event.experience
            }
        }
    }

    private fun submitFeedback() {
        if (ratingStar == 0 && requestFeature.isEmpty() && experience.isEmpty()) {
            updateError("Please fill all fields, thank you! \uD83D\uDE4F\uD83C\uDF3C")
            return
        }
        addNewFeedBack()
    }

    private fun addNewFeedBack() {
        val feedback = hashMapOf(
            "rating" to ratingStar,
            "feature" to requestFeature,
            "experience" to experience,
            "date" to Utils.getCurrentDateFormat()
        )
        feedbackCollection.document(DOCUMENT_ID)
            .collection(uid)
            .add(feedback)
            .addOnSuccessListener {
                uiState.updateSuccess(success = true)
            }
            .addOnFailureListener { e ->
                updateError("Failed to submit feedback")
            }
    }

    fun clearError() {
        uiState.updateError(error = null)
    }

    fun clearSuccess() {
        uiState.updateSuccess(success = null)
    }

    fun updateError(error: String) {
        uiState.updateError(error)
    }

    companion object {
        private const val TAG = "FeedbackViewModel"
        const val COLLECTION_FEEDBACK = "SimpleUnitConvertFeedback"
        const val DOCUMENT_ID = "feedback"
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

