package com.example.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.unitConvertScenarios() {
  // -----------------
  // UnitConvert Home
  // -----------------
  exploreUnitConvertHome()
  navigateFromHomeCalculator()

  // -----------------
  // UnitConvert Details
  // -----------------
  calculatorsWaitForContent()
}

fun MacrobenchmarkScope.calculatorsWaitForContent() = device.apply {
  wait(Until.hasObject(By.res("UnitCalculate")), 15_000L)
}


fun MacrobenchmarkScope.navigateFromHomeCalculator() = device.apply {
  waitAndFindObject(By.res("itemConvert"), 15_000L).click()
  waitForIdle()
}

fun MacrobenchmarkScope.exploreUnitConvertHome() = device.apply {
  homeWaitForContent()
  unitConvertListScrollDownUp()
}

fun MacrobenchmarkScope.homeWaitForContent() = device.apply {
  wait(Until.hasObject(By.res("HomeUnitList")), 15_000L)
}

fun MacrobenchmarkScope.unitConvertListScrollDownUp() = device.apply {
  val channelList = waitAndFindObject(By.res("HomeUnitList"), 15_000L)
  flingElementDownUp(channelList)
}

internal fun UiDevice.flingElementDownUp(element: UiObject2) {
    // Set some margin from the sides to prevent triggering system navigation
    element.setGestureMargin(displayWidth / 5)

    element.fling(Direction.DOWN)
    waitForIdle()
    element.fling(Direction.UP)
}

internal fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long = 15_000L): UiObject2 {
    if (!wait(Until.hasObject(selector), timeout)) {
        throw AssertionError("Element not found on screen in ${timeout}ms (selector=$selector)")
    }

    return findObject(selector)
}
