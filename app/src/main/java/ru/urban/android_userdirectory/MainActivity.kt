package ru.urban.android_userdirectory

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var nameEditTextET: EditText
    private lateinit var ageEditTextET: EditText

    private lateinit var saveBTN: Button

    private lateinit var listViewLV: ListView

    private val list: MutableList<User> = mutableListOf()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        nameEditTextET = findViewById(R.id.nameEditTextET)
        ageEditTextET = findViewById(R.id.ageEditTextET)

        saveBTN = findViewById(R.id.saveBTN)

        listViewLV = findViewById(R.id.listViewLV)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1,list)
        listViewLV.adapter = adapter

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, v, position, id ->
                adapter.remove(adapter.getItem(position))
                Toast.makeText(this, "Пользователь удален", Toast.LENGTH_SHORT).show()
            }

        saveBTN.setOnClickListener { view ->
            list.add(User(nameEditTextET.text.toString(), ageEditTextET.text.toString().toInt()))
            nameEditTextET.text.clear()
            ageEditTextET.text.clear()

            adapter.notifyDataSetInvalidated()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){

            R.id.exitMainMenu -> {
                Toast.makeText(
                    applicationContext,
                    "Приложение закрыто",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}