package ru.urban.android_userdirectory

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment

class MyDialog: DialogFragment() {

    private var removable: Removable? = null

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        removable = context as Removable?
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val user = requireArguments().getParcelable<User>("user")
        val builder = AlertDialog.Builder(requireActivity())
        return builder.setTitle("Удаление!")
            .setMessage("Вы точно хотите удалить заметку $user?")
            .setNegativeButton("Нет",null)
            .setPositiveButton("Да"){ dialog, which ->
                removable?.remove(user as User)
                Toast.makeText(context, "Пользователь удален", Toast.LENGTH_SHORT).show()
            }
            .create()
    }
}