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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.marcel.socialmediaapp.databinding.ActivityRejestracjaBinding;

public class Rejestracja extends AppCompatActivity {
    ActivityRejestracjaBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRejestracjaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        binding.buttonZatwierdz.setOnClickListener(this::signUp);
    }
    public void signUp(View view){
        String mail=binding.editTextMail.getText().toString();
        String haslo=binding.editTextHaslo.getText().toString();
        if(!mail.isEmpty()&&!haslo.isEmpty()){
        mAuth.createUserWithEmailAndPassword(mail,haslo).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Rejestracja.this, "Poprawnie zarejestrowano", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    UserProfileChangeRequest profileUpdate= new UserProfileChangeRequest.Builder()
                            .setDisplayName(binding.editTextLogin.getText().toString()).build();
                    user.updateProfile(profileUpdate);
                    Intent intent= new Intent(Rejestracja.this,MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Log.w("tag", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(Rejestracja.this, "Blad", Toast.LENGTH_SHORT).show();
                }
            }
        });
        }
    }
}
