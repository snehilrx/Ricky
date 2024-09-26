package com.snehil.ricky.ui.characters.details

import com.snehil.ricky.db.CharacterDao
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val characterDao: CharacterDao
) {
    fun getCharacter(id: Int) = characterDao.getCharacter(id)

}