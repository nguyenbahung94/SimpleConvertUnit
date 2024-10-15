package com.billy.simpleunitconvert.feature.home

import androidx.compose.runtime.Stable
import com.billy.simpleunitconvert.core.model.HomeUnit
import com.billy.simpleunitconvert.core.model.UnitConvert
import com.billy.simpleunitconvert.core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

): BaseViewModel() {


   fun getDummyDataHomeUnit(): StateFlow<List<HomeUnit>> {
    val dummyData = listOf(
        HomeUnit("Common", listOf(
            UnitConvert("image1", "name 1", "category"),
            UnitConvert("image2", "name 2", "category"),
            UnitConvert("image3", "name 3", "category"),
            UnitConvert("image11", "name 11", "category"),
            UnitConvert("image22", "name 22", "category"),
            UnitConvert("image33", "name 33", "category"),
            UnitConvert("image11", "name 11", "category"),
            UnitConvert("image22", "name 22", "category"),
            UnitConvert("image33", "name 33", "category")
        ).toImmutableList()),
        HomeUnit("Rare", listOf(
            UnitConvert("image4", "name 4", "category"),
            UnitConvert("image5", "name 5", "category"),
            UnitConvert("image6", "name 6", "category"),
            UnitConvert("image11", "name 11", "category"),
            UnitConvert("image22", "name 22", "category"),
            UnitConvert("image33", "name 33", "category")
        ).toImmutableList()),
        HomeUnit("Legendary", listOf(
            UnitConvert("image7", "name 7", "category"),
            UnitConvert("image8", "name 8", "category"),
            UnitConvert("image9", "name 9", "category"),
            UnitConvert("image11", "name 11", "category"),
            UnitConvert("image22", "name 22", "category"),
            UnitConvert("image33", "name 33", "category")
        ).toImmutableList())
    )
    return MutableStateFlow(dummyData)
}


}



