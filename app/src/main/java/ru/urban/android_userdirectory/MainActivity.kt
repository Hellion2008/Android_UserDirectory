package ru.urban.android_userdirectory

import android.annotation.SuppressLint
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
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity(), Removable {

    private lateinit var nameEditTextET: EditText
    private lateinit var ageEditTextET: EditText

    private lateinit var saveBTN: Button

    private lateinit var listViewLV: ListView

    private lateinit var list: MutableList<User>

    private var adapter: ArrayAdapter<User>? = null

    private lateinit var userViewModel: UserViewModel

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

        setSupportActionBar(findViewById(R.id.toolbarMain))

        nameEditTextET = findViewById(R.id.nameEditTextET)
        ageEditTextET = findViewById(R.id.ageEditTextET)

        saveBTN = findViewById(R.id.saveBTN)

        listViewLV = findViewById(R.id.listViewLV)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        userViewModel.current.observe(this, {
            adapter =
                ArrayAdapter(this, android.R.layout.simple_list_item_1, it)
            listViewLV.adapter = adapter
            adapter?.notifyDataSetChanged()
        })

        listViewLV.onItemClickListener =
            AdapterView.OnItemClickListener{ parent, v, position, id ->
                val user = adapter!!.getItem(position)
                val dialog = MyDialog()
                val args = Bundle()
                args.putParcelable("user", user)
                dialog.arguments = args
                dialog.show(supportFragmentManager, "custom")
            }

        saveBTN.setOnClickListener { view ->
            if (nameEditTextET.text.isEmpty() || ageEditTextET.text.isEmpty()
                || !ageEditTextET.text.isDigitsOnly()) {

                Toast.makeText(this, "Неправильный формат текста", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            list = userViewModel.current.value ?: mutableListOf()
            userViewModel.current.value = list
            list.add(User(nameEditTextET.text.toString(), ageEditTextET.text.toString().toInt()))
            nameEditTextET.text.clear()
            ageEditTextET.text.clear()

            adapter!!.notifyDataSetChanged()
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

    override fun remove(user: User?) {
        adapter?.remove(user)
        adapter?.notifyDataSetChanged()
    }
}