package com.janpschwietzer.calpal.data.repository

import com.janpschwietzer.calpal.data.model.UserModel
import com.janpschwietzer.calpal.data.model.toUserEntity
import com.janpschwietzer.calpal.data.source.local.UserDao
import com.janpschwietzer.calpal.data.source.local.toUserModel

interface UserRepository {
    suspend fun saveUser(user: UserModel)
    suspend fun getUser(): UserModel?
}

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun saveUser(user: UserModel) {
        userDao.insertUser(user.toUserEntity())
    }

    override suspend fun getUser(): UserModel? {
        return userDao.getUser()?.toUserModel()
    }

}