package com.example.examples.searchable_list

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


class ListViewModel: ViewModel() {

    private val items = listOf(
        Item("London"),
        Item("Pizza"),
        Item("Football"),
        Item("Painting"),
        Item("Guitar"),
        Item("Running"),
        Item("Coffee"),
        Item("Dancing"),
        Item("Mountains"),
        Item("Yoga"),
    )

    private var _filteredItems: List<Item> by mutableStateOf(items)
    val filteredItems: List<Item>
        get() = _filteredItems

    private var _searchText: TextFieldValue by mutableStateOf(TextFieldValue())
    val searchText: TextFieldValue
        get() = _searchText

    fun handleSearch(searchValue: TextFieldValue){
        _searchText = searchValue
        _filteredItems = items.filter {
            it.name.contains(searchValue.text, ignoreCase = true)
        }
    }

}