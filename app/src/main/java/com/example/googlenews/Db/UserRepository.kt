package com.example.googlenews.Db

import androidx.lifecycle.LiveData

class UserRepository(val userDao: UserDao) {
    val readAlldata: LiveData<List<User>> = userDao.readAllData()
    suspend fun addUser(user: User) {
        userDao.addUser(user)
    }

    suspend fun delete(user: User) {
        userDao.delete(user)
    }
}