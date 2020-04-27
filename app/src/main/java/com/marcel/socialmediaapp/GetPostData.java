package com.marcel.socialmediaapp;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class GetPostData {
    public interface MyCallback {
        void onCallback(ArrayList<MainModel> a, ArrayList<String>b);
    }
    public interface LikeCallback{
       void onCallback(boolean a,String id);
    }
    private FirebaseAuth mAuth;

ArrayList<MainModel> lista;
ArrayList<String> id;
boolean result=false;
String idd="";
    public GetPostData() {
        mAuth = FirebaseAuth.getInstance();
        lista=new ArrayList<>();
        id=new ArrayList<>();

    }
    public void check_like(LikeCallback likeCallback, String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

            db.collection("Posty").document(id).collection("like").get().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String u=document.getId();

                        if(u.equals(currentUser.getUid())){
                            Log.i("idd","idd");
                           result=true;
                           idd=document.getId();
                           Log.i("idd",idd);
                           break;
                        }
                    }
                }else{
                    Log.i("idd",task.getException().toString());
                }
                likeCallback.onCallback(result,idd);
            });


    }
public void readData(MyCallback myCallback){
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    db.collection("Posty").orderBy("data", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
        int i =0;
        if(task.isSuccessful()){
            MainActivity mainActivity= new MainActivity();
            for (QueryDocumentSnapshot document : task.getResult()) {
                Log.d("tag", document.getId() + " => " + document.getData());
                mainActivity.lista.add(document.toObject(MainModel.class));
                lista.add(document.toObject(MainModel.class));
                id.add(document.getId());
            }

        } else {
            Log.w("tag", "Error getting documents.", task.getException());
        }

        myCallback.onCallback(lista,id);
    });
}

}
