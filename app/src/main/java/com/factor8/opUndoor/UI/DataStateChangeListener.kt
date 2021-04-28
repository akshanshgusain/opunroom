package com.factor8.opUndoor.UI

interface DataStateChangeListener{

    fun onDataStateChange(dataState: DataState<*>?)

    fun isStoragePermissionGranted(): Boolean

    fun hideSoftKeyboard()
}