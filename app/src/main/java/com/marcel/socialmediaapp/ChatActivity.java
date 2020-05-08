package com.marcel.socialmediaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.marcel.socialmediaapp.databinding.ActivityChatBinding;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;
    MessagesAdapter messagesAdapter;
    ArrayList<MessageModel> messageModels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding=ActivityChatBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        LinearLayoutManager layoutManager =new LinearLayoutManager(ChatActivity.this,LinearLayoutManager.VERTICAL,false);
        binding.RecyclerMessages.setLayoutManager(layoutManager);
        binding.RecyclerMessages.setItemAnimator(new DefaultItemAnimator());

        String userName=getIntent().getStringExtra("Nazwa");
        String userID = getIntent().getStringExtra("UID");
        binding.toolbarTextName.setText(userName);
        messageModels=new ArrayList<>();
        MessageModel a=new MessageModel("Marcel Barski","bCWg2mxbTweAikNhH6G5VLIna3m2","K4dZSAWZgoQznnsikn9BmPAryZY2","test1",null);
        MessageModel b=new MessageModel("Karol","K4dZSAWZgoQznnsikn9BmPAryZY2","bCWg2mxbTweAikNhH6G5VLIna3m2","test2",null);
        messageModels.add(a);
        messageModels.add(b);

        messagesAdapter=new MessagesAdapter(messageModels,this,userID);
        binding.RecyclerMessages.setAdapter(messagesAdapter);
        messagesAdapter.setMessageModels(messageModels);
        messagesAdapter.notifyDataSetChanged();
    }
}
