package com.dioses.usersp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dioses.usersp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        val isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time), true)

        if (isFirstTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(
                    R.string.dialog_confirm
                ) { _, _ ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.etUsername)
                        .text.toString()
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                }
                .setNeutralButton(getString(R.string.dialog_guest)) { dialogInterface, _ ->
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), true).apply()
                    }
                    dialogInterface.dismiss()
                    Toast.makeText(this, R.string.welcome_guest, Toast.LENGTH_SHORT).show()
                }
                .show()
        } else {
            val username =
                preferences.getString(
                    getString(R.string.sp_username),
                    getString(R.string.hint_username)
                )
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()

        }

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()
        val arthur = User(
            1,
            "Arthur",
            "Dioses",
            "https://frogames.es/wp-content/uploads/2020/09/alain-1.jpg"
        )
        val samanta = User(
            2,
            "Samanta",
            "Meza",
            "https://upload.wikimedia.org/wikipedia/commons/b/b2/Samanta_villar.jpg"
        )
        val wendy = User(
            3,
            "Wendy",
            "Castro",
            "https://upload.wikimedia.org/wikipedia/commons/d/d9/Emma_Wortelboer_%282018%29.jpg"
        )
        val alain = User(
            4,
            "Alain",
            "Nicol??s",
            "https://frogames.es/wp-content/uploads/2020/09/alain-1.jpg"
        )
        val javier = User(
            5,
            "Javier",
            "G??mez",
            "https://live.staticflickr.com/974/42098804942_b9ce35b1c8_b.jpg"
        )
        val emma = User(
            6,
            "Emma",
            "Cruz",
            "https://upload.wikimedia.org/wikipedia/commons/d/d9/Emma_Wortelboer_%282018%29.jpg"
        )

        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(arthur)
        users.add(samanta)
        users.add(wendy)

        return users
    }

    override fun onclick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }
}