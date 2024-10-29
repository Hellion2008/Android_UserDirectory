package ru.urban.android_userdirectory

data class User(val name: String, var age: Int) {
    override fun toString(): String {
        return "$name: $age"
    }
}