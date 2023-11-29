package com.example.finalprojectbinar.view.model_dummy

sealed class ListFilter {
    data class HeaderItem(val text: String):ListFilter()
    data class CheckboxItem(val text: String):ListFilter()
}
