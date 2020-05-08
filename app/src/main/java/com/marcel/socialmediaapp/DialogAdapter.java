package com.marcel.socialmediaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {
    ArrayList<CommentModel> commentModels;
    Context context;

    public DialogAdapter(ArrayList<CommentModel> commentModels, Context context) {
        this.commentModels = commentModels;
        this.context = context;
    }

    public ArrayList<CommentModel> getCommentModels() {
        return commentModels;
    }

    public void setCommentModels(ArrayList<CommentModel> commentModels) {
        this.commentModels = commentModels;
    }

    @NonNull
    @Override
    public DialogAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DialogAdapter.ViewHolder holder, int position) {
        holder.tresc.setText(commentModels.get(position).getTresc());
        holder.imie.setText(commentModels.get(position).getAutor());
    }

    @Override
    public int getItemCount() {
        return commentModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView imie;
        TextView tresc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imie =itemView.findViewById(R.id.commNick);
            tresc=itemView.findViewById(R.id.commBody);
        }
    }
}
