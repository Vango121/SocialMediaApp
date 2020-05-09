package com.marcel.socialmediaapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<MessageModel> messageModels;
    private Context context;
    private String userID;
    private String name;
    private String senderName;

    public MessagesAdapter(ArrayList<MessageModel> messageModels, Context context, String userID, String name, String senderName) {
        this.messageModels = messageModels;
        this.context = context;
        this.userID = userID;
        this.name = name;
        this.senderName=senderName;
    }

    public MessagesAdapter(ArrayList<MessageModel> messageModels, Context context, String userID) {
        this.messageModels = messageModels;
        this.context=context;
        this.userID=userID;
    }

    public ArrayList<MessageModel> getMessageModels() {
        return messageModels;
    }

    public void setMessageModels(ArrayList<MessageModel> messageModels) {
        this.messageModels = messageModels;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType==UserManagment.RECIEVER){
            view= LayoutInflater.from(context).inflate(R.layout.comment_items,parent,false);
            return new ViewHolderRecieved(view);
        }else{
            view= LayoutInflater.from(context).inflate(R.layout.send_message,parent,false);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Log.i("id",position+"");
        if(getItemViewType(position)==UserManagment.RECIEVER){
            ((ViewHolderRecieved)holder).setDetailsRecieved(messageModels.get(position));
        }else if(getItemViewType(position)==UserManagment.SENDER){
            ((ViewHolder)holder).setDetails(messageModels.get(position));
        }
        else{
            ((ViewHolder)holder).hideElements();
        }
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }

    @Override
    public int getItemViewType(int position) {
        UserManagment userManagment= new UserManagment();
        int result=userManagment.checkMessageUser(messageModels.get(position),userID);
        if(result==0){
            messageModels.get(position).setNazwa(name);
            Log.i("nazwa",name);
        }else if(result==1){
            messageModels.get(position).setNazwa(senderName);
            Log.i("nazwa",senderName);
        }
        Log.i("result",result+"");
        return result;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView imie;
        private TextView message;
        private ImageView profil;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imie=itemView.findViewById(R.id.commNickSent);
            message=itemView.findViewById(R.id.commBodySent);
            profil=itemView.findViewById(R.id.profilePhoto);
        }
        private void hideElements(){
            imie.setVisibility(View.GONE);
            message.setVisibility(View.GONE);
            profil.setVisibility(View.GONE);
        }
        private void setDetails(MessageModel messageModel){
            imie.setText(messageModel.getNazwa());
            message.setText(messageModel.getWiadomosc());
        }
    }
    public class ViewHolderRecieved extends RecyclerView.ViewHolder {
        private TextView imie1;
        private TextView message1;
        public ViewHolderRecieved(@NonNull View itemView) {
            super(itemView);
            imie1=itemView.findViewById(R.id.commNick);
            message1=itemView.findViewById(R.id.commBody);
        }
        private void setDetailsRecieved(MessageModel messageModel){
            imie1.setText(messageModel.getNazwa());
            message1.setText(messageModel.getWiadomosc());
        }
    }
}
