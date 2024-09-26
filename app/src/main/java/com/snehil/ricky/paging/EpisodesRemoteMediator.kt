package com.snehil.ricky.paging

import androidx.room.withTransaction
import com.snehil.ricky.api.RickAndMortyApi
import com.snehil.ricky.api.model.Character
import com.snehil.ricky.api.model.Response
import com.snehil.ricky.db.CharacterDao
import com.snehil.ricky.db.RickyDb
import com.snehil.ricky.db.model.CharacterEntity
import com.snehil.ricky.db.model.PartialCharacterEntity
import javax.inject.Inject

class EpisodesRemoteMediator @Inject constructor(
    private val api: RickAndMortyApi,
    private val dao: CharacterDao,
    private val db: RickyDb
) : ResponseRemoteMediator<Character, CharacterEntity>() {

    override suspend fun getResponse(loadKey: Int): Result<Response<Character>> {
        return if (loadKey > 0) {
            api.getCharacters(loadKey)
        } else {
            Result.failure(Exception())
        }
    }

    override fun key(item: CharacterEntity?) = item?.pageNo ?: 1

    override suspend fun saveResponse(response: Response<Character>) {
        val pageNo = response.info?.next?.substringAfterLast("=")?.toIntOrNull() ?: -1
        db.withTransaction {
            dao.insertAll(response.results
                .map {
                    PartialCharacterEntity(
                        it.id ?: -1,
                        it.name,
                        it.status,
                        it.species,
                        it.type,
                        it.gender,
                        it.origin?.name,
                        it.location?.name,
                        it.image,
                        it.episode.firstOrNull(),
                        it.created,
                        pageNo
                    )
                })
        }

    }
}