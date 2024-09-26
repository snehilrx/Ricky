package com.snehil.ricky.ui.characters.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersListViewModel @Inject constructor(private val charactersListRepository: CharactersListRepository) :
    ViewModel() {
    fun toggleFavourite(id: Int?) {
        if (id != null) {
            viewModelScope.launch {
                charactersListRepository.toggleFavourite(id)
            }
        }
    }

    fun pager(query: String? = null) =
        charactersListRepository.episodePager(query).flow.cachedIn(viewModelScope)

    fun loadEpisodeName(characterId: Int?, episodeId: Int?) {
        if (episodeId != null && characterId != null) {
            viewModelScope.launch {
                charactersListRepository.loadEpisodeName(episodeId)
            }
        }
    }

    fun getEpisodeName(episodeId: Int?) = charactersListRepository.getEpisodeName(episodeId)
}