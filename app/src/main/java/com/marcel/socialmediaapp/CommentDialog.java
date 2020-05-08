package com.marcel.socialmediaapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CommentDialog extends DialogFragment {
    String a="";
    Button send;
    EditText value;
    RecyclerView recyclerView;
    DialogAdapter dialogAdapter;
    ArrayList<CommentModel> commentModels;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.comment_dialog,null);
        send=view.findViewById(R.id.sendComment);
        send.setOnClickListener(this::send);
        value=view.findViewById(R.id.editTextComment);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView=view.findViewById(R.id.recyclerComment);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        commentModels = new ArrayList<>();
        dialogAdapter=new DialogAdapter(commentModels,getContext());
        recyclerView.setAdapter(dialogAdapter);
        builder.setView(view)
        .setTitle("Komentarze").setPositiveButton("Zamknij", (dialog, which) -> {
            dialog.dismiss();
        });

        return builder.create();
    }
    public void sendData(String s){
        a=s;
    }
    void updateData(){
        GetCommentData getCommentData = new GetCommentData();
        getCommentData.readData(b->{
            commentModels=b;
            dialogAdapter.setCommentModels(commentModels);
            dialogAdapter.notifyDataSetChanged();
        },a);
    }
    public void send(View view){
        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        Date todayDate = Calendar.getInstance().getTime();
        Timestamp timestamp= new Timestamp(todayDate);
        CommentModel commentModel=new CommentModel(value.getText().toString(),currentUser.getDisplayName(),timestamp);
        commentModels.add(commentModel);
        dialogAdapter.setCommentModels(commentModels);
        dialogAdapter.notifyDataSetChanged();
        db.collection("Posty").document(a).collection("komentarze").add(commentModel).addOnCompleteListener(task->{
        });
        db.collection("Posty").document(a).update("komentarze", FieldValue.increment(1));
        value.setText("");
    }
}
