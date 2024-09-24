package com.snehil.ricky.ui.components

import androidx.lifecycle.ViewModel
import com.snehil.ricky.db.RickAndMortyDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val dao: RickAndMortyDao
) : ViewModel() {

    suspend fun getLaunch(id: String) = dao.getLaunch(id)
}