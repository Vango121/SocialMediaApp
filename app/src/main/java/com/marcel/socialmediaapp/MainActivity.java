package com.marcel.socialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.WriteResult;
import com.marcel.socialmediaapp.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {
    ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    ArrayList<MainModel>lista= new ArrayList<>();
    MainAdapter mainAdapter;
    FirebaseUser currentUser;
    ArrayList<String>id=new ArrayList<>();
    boolean check = false;
    boolean comm=false;
    int nr;
    String saveId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        binding.wyloguj.setOnClickListener(this::wylog);
        binding.imageButton.setOnClickListener(this::userListButton);
        LinearLayoutManager layoutManager =new LinearLayoutManager(MainActivity.this,LinearLayoutManager.VERTICAL,false);
        binding.Recycler.setLayoutManager(layoutManager);
        binding.Recycler.setItemAnimator(new DefaultItemAnimator());
         mainAdapter=new MainAdapter(MainActivity.this, lista, (position) -> {
             GetPostData getPostData = new GetPostData();
             FirebaseFirestore db = FirebaseFirestore.getInstance();
             DocumentReference documentReference = db.collection("Posty").document(id.get(position));
             nr=position;

                 getPostData.check_like((a, b) -> {
                     check = a;
                     //idlike=b;
                     if (check == false) {
                         Log.i("id", id.get(position));
                         documentReference.update("like", lista.get(position).getLike() + 1).addOnSuccessListener(aVoid -> {
                             //Log.i("Like","done");
                             mainAdapter.notifyItemChanged(position);
                             //check=true;
                         });
                         Log.i("Like1", "done");
                         mainAdapter.mainModels.get(position).setLike(lista.get(position).getLike() + 1);
                         db.collection("Posty").document(id.get(position)).collection("like").document(currentUser.getUid()).set(new HashMap<String, Object>()).addOnCompleteListener(task -> {
                             Log.i("blad", String.valueOf(task.getException()));
                         });

                     }
                     if (check == true) {
                         Log.i("id", id.get(position));
                         documentReference.update("like", lista.get(position).getLike() - 1).addOnSuccessListener(aVoid -> {
                             //Log.i("Like","done");
                             mainAdapter.notifyItemChanged(position);
                         });
                         mainAdapter.mainModels.get(position).setLike(lista.get(position).getLike() - 1);
                         db.collection("Posty").document(id.get(position)).collection("like").document(currentUser.getUid()).delete();
                         //check=false;
                     }
                 }, id.get(position));




         },comListener->{
             CommentDialog commentDialog=new CommentDialog();
             commentDialog.sendData(id.get(comListener));
             commentDialog.show(getSupportFragmentManager(),null);
             commentDialog.updateData();
             //saveId=id.get(comListener);
         });
        binding.Recycler.setAdapter(mainAdapter);
        GetPostData getPostData=new GetPostData();
        getPostData.readData((a ,b)   -> {
            id=b;
            lista=a;
            mainAdapter.setMainModels(lista);
            Log.i("main",lista.get(0).getTresc());
            mainAdapter.notifyDataSetChanged();
        });
        binding.buttonUpload.setOnClickListener(this::sendPost);
    }



    public void sendPost(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        MainModel mainModel = new MainModel();
        mainModel.setNazwa(currentUser.getDisplayName());
        mainModel.setUzytkownik(currentUser.getUid());
        mainModel.setTresc(binding.editTextPost.getText().toString());
        binding.editTextPost.setText("");
        Date todayDate = Calendar.getInstance().getTime();
        Timestamp timestamp= new Timestamp(todayDate);
        mainModel.setData(timestamp);
        mainModel.setLike(0);
        mainModel.setKomentarze(0);
        db.collection("Posty").add(mainModel).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(MainActivity.this, "Post dodany", Toast.LENGTH_SHORT).show();
                GetPostData getPostData=new GetPostData();
                getPostData.readData((a,b) -> {
                    lista=a;
                    mainAdapter.setMainModels(lista);
                    Log.i("main",lista.get(0).getTresc());
                    mainAdapter.notifyDataSetChanged();
                });
            }
        });

    }
    public void userListButton(View view){
        Intent intent = new Intent(MainActivity.this,ContactActivity.class);
        startActivity(intent);
    }
    public void wylog(View view){
        mAuth.signOut();
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onStart() {
        super.onStart();
         currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }else {

        }
    }

}
