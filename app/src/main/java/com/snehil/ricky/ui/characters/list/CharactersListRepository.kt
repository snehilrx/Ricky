package com.snehil.ricky.ui.characters.list

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.snehil.ricky.Constants
import com.snehil.ricky.api.RickAndMortyApi
import com.snehil.ricky.db.CharacterDao
import com.snehil.ricky.db.model.EpisodeNameEntity
import com.snehil.ricky.paging.EpisodesRemoteMediator
import javax.inject.Inject

class CharactersListRepository @Inject constructor(
    private val episodesRemoteMediator: EpisodesRemoteMediator,
    private val api: RickAndMortyApi,
    private val dao: CharacterDao
) {
    suspend fun toggleFavourite(id: Int) {
        dao.toggleFavourite(id)
    }

    @OptIn(ExperimentalPagingApi::class)
    fun episodePager(query: String?) = Pager(
        config = PagingConfig(
            pageSize = Constants.NETWORK_PAGING_SIZE,
            enablePlaceholders = true,
        ),
        remoteMediator = episodesRemoteMediator
    ) {
        if (!query.isNullOrBlank()) {
            dao.getAllCharacters("%$query%")
        } else {
            dao.getAllCharacters()
        }
    }

    suspend fun loadEpisodeName(episodeId: Int) {
        val episodeName = dao.getEpisodeName(episodeId)
        if (episodeName == null) {
            val episode = api.getEpisode(episodeId).getOrNull()?.name
            if (episode != null) {
                dao.insertEpisodeName(EpisodeNameEntity(episodeId, episode))
            }
        }
    }

    fun getEpisodeName(episodeId: Int?) = dao.getEpisodeNameFlow(episodeId)
}