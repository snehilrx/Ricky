package com.snehil.ricky.ui.characters.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snehil.ricky.ui.characters.list.CharactersListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: DetailsRepository,
    private val episodeRepository: CharactersListRepository
) : ViewModel() {

    fun getCharacter(id: Int) = repository.getCharacter(id)
    fun toggleFavourite(id: Int) = viewModelScope.launch {
        episodeRepository.toggleFavourite(id)
    }


}
