package com.example.googlenews


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    lateinit var mbutton: Button
    lateinit var mbtngoogle: ImageView
    lateinit var mUsername: EditText
    lateinit var mPassword: EditText
    lateinit var firebase: FirebaseAuth
    lateinit var mAuth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mUsername = findViewById(R.id.etUserId)
        mPassword = findViewById(R.id.etPassword)
        mbutton = findViewById(R.id.button)
        mbtngoogle = findViewById(R.id.ivGoogle)
        mAuth = FirebaseAuth.getInstance()
        createRequest()
        firebase = FirebaseAuth.getInstance()
        mbutton.setOnClickListener(View.OnClickListener {
            val username: String
            val password: String
            username = mUsername.getText().toString()
            password = mPassword.getText().toString()
            firebase.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@LoginActivity, "Login Succesful", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this@LoginActivity, "Login Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        })
        mbtngoogle.setOnClickListener(View.OnClickListener {
            //TODO Check google playservices is updated or not or Exist
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
            Log.e("DEBUG", "START-ONCLICK")
        })

    }
    private fun createRequest() {
        // Configure Google Sign In
        Log.e("DEBUG", "START")
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                Log.e(ContentValues.TAG, "try block")
                val account = task.getResult(ApiException::class.java)
                Log.e(ContentValues.TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = mAuth.currentUser
                    Log.w(ContentValues.TAG, "signInWithCredential:Successful ", task.exception)
                    val intent = Intent(applicationContext, HomePageActivity::class.java)
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(ContentValues.TAG, "signInWithCredential:failure ", task.exception)
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 12345
    }
}