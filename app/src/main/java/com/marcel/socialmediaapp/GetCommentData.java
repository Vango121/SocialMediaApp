package com.marcel.socialmediaapp;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GetCommentData {
    FirebaseUser currentUser;
    public interface Callback{
        void onCallback(ArrayList<CommentModel> list);
    }
    private FirebaseAuth mAuth;
    ArrayList<CommentModel> list;
    public GetCommentData(){
        mAuth = FirebaseAuth.getInstance();
        list=new ArrayList<>();
    }
    void readData(Callback callback,String id){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Posty").document(id).collection("komentarze").orderBy("data", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                for(QueryDocumentSnapshot document:task.getResult()){
                    list.add(document.toObject(CommentModel.class));
                }
            }
            callback.onCallback(list);
        });
    }

}
