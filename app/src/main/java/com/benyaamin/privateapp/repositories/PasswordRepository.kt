package com.benyaamin.privateapp.repositories

import com.benyaamin.privateapp.models.Password
import com.benyaamin.privateapp.persistence.PasswordDao
import javax.inject.Inject

class PasswordRepository @Inject constructor(
    private val passwordDao: PasswordDao
) {

    suspend fun getPasswords(): List<Password> {
        return passwordDao.getPasswords()
    }

    suspend fun insertPassword(password: Password) {
        passwordDao.insert(password)
    }

    suspend fun deletePassword(password: Password) {
        passwordDao.delete(password)
    }

}