package com.marcel.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.marcel.socialmediaapp.databinding.ActivityLoginBinding;
import com.marcel.socialmediaapp.databinding.ActivityMainBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        binding= ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.buttonRejestracja.setOnClickListener(this::rejestruj);
        binding.buttonLogin.setOnClickListener(this::login);
        mAuth = FirebaseAuth.getInstance();
    }
public void login(View view){
        mAuth.signInWithEmailAndPassword(binding.editText.getText().toString(),binding.editText2.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user=mAuth.getCurrentUser();
                    Toast.makeText(LoginActivity.this, "Hi "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, "Zalogowano", Toast.LENGTH_SHORT).show();
                }else{
                    Log.w("tag", "createUserWithEmail:failure", task.getException());
                }
            }
        });

}
    public void rejestruj(View view){
        Intent intent=new Intent(LoginActivity.this,Rejestracja.class);
        startActivity(intent);
    }
}
