package com.example.belajarfirebaseb

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.belajarfirebaseb.databinding.ActivityLoginBinding
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth

private val <ActivityLoginBinding> ActivityLoginBinding.root: Any
    get() {}

class LoginActivity<ActivityLoginBinding> : AppCompatActivity(), View.OnClickListener {

    private var auth: FirebaseAuth? = null
    private val RC_SIGN_IN = 1
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progress.visibility = View.GONE
        binding.login.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()

        if(auth!!.currentUser != null){
            // Perbaiki: Tambahkan flags untuk clear back stack
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }

    override fun onClick(p0: View?) {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.GoogleBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Login Dibatalkan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
