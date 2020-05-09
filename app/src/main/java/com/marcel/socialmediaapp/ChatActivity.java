package com.marcel.socialmediaapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.marcel.socialmediaapp.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    MessagesAdapter messagesAdapter;
    ArrayList<MessageModel> messageModels;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String userID;
    String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        LinearLayoutManager layoutManager =new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,false);
        binding.RecyclerMessages.setLayoutManager(layoutManager);
        binding.RecyclerMessages.setItemAnimator(new DefaultItemAnimator());
        binding.buttonSendMsg.setOnClickListener(this::sendMsg); //onclick listener


         userName=getIntent().getStringExtra("Nazwa");
        userID = getIntent().getStringExtra("UID");
        binding.toolbarTextName.setText(userName);
        messageModels=new ArrayList<>();
//        MessageModel a=new MessageModel("Marcel Barski","bCWg2mxbTweAikNhH6G5VLIna3m2","K4dZSAWZgoQznnsikn9BmPAryZY2","test1",null);
//        MessageModel b=new MessageModel("Karol","K4dZSAWZgoQznnsikn9BmPAryZY2","bCWg2mxbTweAikNhH6G5VLIna3m2","test2",null);
//        MessageModel c=new MessageModel("Karol12","FlTY92qtMzL8LMh9kil8uomX2zI3","K4dZSAWZgoQznnsikn9BmPAryZY2","test45",null);
//        MessageModel d=new MessageModel("Karol12","K4dZSAWZgoQznnsikn9BmPAryZY2","FlTY92qtMzL8LMh9kil8uomX2zI3","test5",null);
//        messageModels.add(a);
//        messageModels.add(b);
//        messageModels.add(c);
//        messageModels.add(d);
        update_messages_data();
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        assert currentUser != null;
        messagesAdapter=new MessagesAdapter(messageModels,this,userID,userName,currentUser.getDisplayName());
        Toast.makeText(this, currentUser.getDisplayName(), Toast.LENGTH_SHORT).show();
        binding.RecyclerMessages.setAdapter(messagesAdapter);
        messagesAdapter.setMessageModels(messageModels);
        messagesAdapter.notifyDataSetChanged();
    }

    public void sendMsg(View view){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
       String msg= binding.editTextMessage.getText().toString();
        Date todayDate = Calendar.getInstance().getTime();
        Timestamp timestamp= new Timestamp(todayDate);
        MessageModel messageModel= new MessageModel();
        messageModel.setData(timestamp);
        messageModel.setWiadomosc(msg);
        messageModel.setSenderUID(currentUser.getUid());
        messageModel.setRecieverUID(userID);
        db.collection("Messages").add(messageModel).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Log.i("msg","wyslana");
                binding.editTextMessage.setText("");
            }
        });
    }
    public void update_messages_data(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        final CollectionReference colRef = db.collection("Messages");
        colRef.addSnapshotListener((queryDocumentSnapshots, e) -> {
            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                switch (dc.getType()) {
                    case ADDED:
                        Log.d("TAG", "New Msg: " + dc.getDocument().toObject(MessageModel.class).getWiadomosc());
                        MessageModel messageModel=dc.getDocument().toObject(MessageModel.class);
                        if((messageModel.getRecieverUID().equals(currentUser.getUid())&&messageModel.getSenderUID().equals(userID))||(messageModel.getSenderUID().equals(currentUser.getUid())&&messageModel.getRecieverUID().equals(userID)))
                            messageModels.add(messageModel);
                            messagesAdapter.notifyDataSetChanged();
                        break;
                }
            }
        });
    }
}
