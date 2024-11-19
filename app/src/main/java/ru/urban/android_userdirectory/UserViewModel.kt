package ru.urban.android_userdirectory

import android.widget.ArrayAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel: ViewModel() {

    val current: MutableLiveData<MutableList<User>>
        by lazy { MutableLiveData<MutableList<User>>() }
}