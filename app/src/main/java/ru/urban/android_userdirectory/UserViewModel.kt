package ru.urban.android_userdirectory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {

    val current: MutableLiveData<MutableList<User>>
        by lazy { MutableLiveData(mutableListOf()) }
}