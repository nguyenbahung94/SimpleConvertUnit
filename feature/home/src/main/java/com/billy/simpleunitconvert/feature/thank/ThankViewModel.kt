package com.billy.simpleunitconvert.feature.thank

import androidx.lifecycle.viewModelScope
import com.billy.simpleunitconvert.core.data.repository.init.CreateDatabaseRepository
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThankViewModel @Inject constructor(
    private val createDatabaseRepository: CreateDatabaseRepository
) : BaseViewModel() {

        fun updateCountOpenApp(count: Int) {
            viewModelScope.launch {
                createDatabaseRepository.updateCountOpenApp(count)
            }
        }

}
