package com.liem.instagram

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private val firebaseDb = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        if (user != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btn_register.setOnClickListener {
            createAccount()
        }
    }

    private fun createAccount() {
        val progresDialog = ProgressDialog(this)
        val fullname = fullname_register.text.toString()
        val userName = username_register.text.toString()
        val email = email_register.text.toString()
        val pass = password_register.text.toString()

        when {
            TextUtils.isEmpty(userName) -> Toast.makeText(this, "Username Email Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(email) -> Toast.makeText(this, "Email Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(pass) -> Toast.makeText(this, " Password Tidak boleh kosong", Toast.LENGTH_SHORT).show()
            TextUtils.isEmpty(fullname) -> Toast.makeText(this, "Fullname Tidak boleh kosong", Toast.LENGTH_SHORT).show()

            else ->
                firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        saveUserInfo(email, fullname, pass, progresDialog)
                        finish()
                    } else {
                        firebaseAuth.signOut()
                        Toast.makeText(this, "Email atau pass salah", Toast.LENGTH_SHORT).show()
                    }

                }
        }
    }
    private fun saveUserInfo(email: String, fullname: String, userName: String, progressDialog: ProgressDialog) {
        val currentUserID = FirebaseAuth.getInstance().currentUser!!.uid
        val usersRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Users")

        val userMap = HashMap<String, Any>()
        userMap["uid"] = currentUserID
        userMap["fullname"] = fullname.toLowerCase()
        userMap["username"] = userName.toLowerCase()
        userMap["email"] = email
        userMap["bio"] = "Hey Iam student at IDN Boarding School"
        //create default image profile
        userMap["image"] = "https://firebasestorage.googleapis.com/v0/b/instagram-app-256b6.appspot.com/o/Default%20Images%2Fprofile.png?alt=media&token=ecebab92-ce4f-463c-a16a-a81fc34b0772"

        usersRef.child(currentUserID).setValue(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful ){
                    progressDialog.dismiss()
                    Toast.makeText(this,"Account sudah dibuat", Toast.LENGTH_LONG).show()

                    //step 16 get post
                    FirebaseDatabase.getInstance().reference
                        .child("Follow").child(currentUserID)
                        .child("Following").child(currentUserID)
                        .setValue(true)

                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else{
                    val message = task.exception!!.toString()
                    Toast.makeText(this,"Error: $message", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                    progressDialog.dismiss()
                }
            }
    }
}