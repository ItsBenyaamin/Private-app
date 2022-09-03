package com.benyaamin.privateapp.repositories

import com.benyaamin.privateapp.models.Password
import com.benyaamin.privateapp.persistence.PasswordDao
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PasswordRepository @Inject constructor(
    private val passwordDao: PasswordDao
) {

    suspend fun getPasswords(): List<Password> {
        return withContext(IO) {
            passwordDao.getPasswords()
        }
    }

    suspend fun insertPassword(password: Password) {
        withContext(IO) {
            passwordDao.insert(password)
        }
    }

    suspend fun updatePassword(password: Password) {
        withContext(IO) {
            passwordDao.update(password)
        }
    }

    suspend fun deletePassword(password: Password) {
        withContext(IO) {
            passwordDao.delete(password)
        }
    }

}