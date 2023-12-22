package com.example.finalprojectbinar.model

sealed class ListItem{
    data class ChapterItem(val chapter: ChapterDetail) : ListItem()
    data class ModuleItem(val module: ModuleDetail) : ListItem()
}
