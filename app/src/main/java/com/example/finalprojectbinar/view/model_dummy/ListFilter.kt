package com.example.finalprojectbinar.view.model_dummy

import com.example.finalprojectbinar.model.DataCategories

sealed class ListFilter {
    data class HeaderItem(val text: String):ListFilter()
    data class CheckboxItem(
        val id: Int?,
        val text: String?
        ):ListFilter()
}
