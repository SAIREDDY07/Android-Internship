package com.example.googlenews.Db

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        readAllData = repository.readAlldata
    }

    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun delete(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(user)
        }

    }

}
//
//class UserViewModel(val repository:UserRepository) : ViewModel() {
//    lateinit var readAlldata: LiveData <List<User>>
//
//fun addUser(user:User){
//    viewModelScope.launch(Dispatchers.IO) {
//        repository.addUser(user)
//    }
//    readAlldata= repository.readAlldata
//}
//
//}
//class UserViewModel(val repository: UserRepository): ViewModel() {
//    val readAlldata: LiveData<List<User>> = repository.readAlldata
//
//    fun addUser(user: User) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.addUser(user)
//        }
//    }
//}
//