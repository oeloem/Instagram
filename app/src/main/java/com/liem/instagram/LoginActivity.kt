package com.liem.instagram

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        // mengecheck userId yang sedang aktif, jika ada, proses akan langsung intent ke hal.utama
        val user = firebaseAuth.currentUser?.uid
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private val progressBar : ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            loginUser()
        }

        btn_signup_link .setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val pass = password_login.text.toString()
        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(pass) -> Toast.makeText(this, "Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            else -> firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {task ->
                progressBar?.visibility = View.VISIBLE
                if (task.isSuccessful) {
                    progressBar?.visibility = View.GONE
                    startActivity(Intent(this, MainActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK))
                    finish()
                } else {
                    progressBar?.visibility = View.GONE
                    firebaseAuth.signOut()
                    Toast.makeText(this, "Email atau pass salah", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)

    }

}