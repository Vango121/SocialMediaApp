package com.marcel.socialmediaapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private final ClickListener listener;
    private final ComListener comlistener;
    ArrayList<MainModel> mainModels;
    Context context;
    public MainAdapter(Context context,ArrayList<MainModel> mainModels,ClickListener listener,ComListener comListener){
        this.context=context;
        this.mainModels=mainModels;
        this.listener=listener;
        this.comlistener=comListener;
    }

    public void setMainModels(ArrayList<MainModel> mainModels) {
        this.mainModels = mainModels;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.textViewPost.setText(mainModels.get(position).getTresc());
        holder.imie.setText(mainModels.get(position).getNazwa());
        holder.komentarze.setText("Komentarze: "+mainModels.get(position).getKomentarze());
        holder.like.setText(mainModels.get(position).getLike()+"");
        Log.i("adapter",mainModels.get(position).getTresc());
    }

    @Override
    public int getItemCount() {
        return mainModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewPost;
        TextView like;
        TextView komentarze;
        TextView imie;
        TextView buttonLike;
        TextView buttonKomment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPost=itemView.findViewById(R.id.TextViewPost);
            like=itemView.findViewById(R.id.textViewLike);
            komentarze=itemView.findViewById(R.id.textViewKom);
            imie=itemView.findViewById(R.id.TextViewName);
            buttonLike=itemView.findViewById(R.id.LikeButton);
            buttonLike.setOnClickListener(likeListener);
            buttonKomment=itemView.findViewById(R.id.CommentButton);
            buttonKomment.setOnClickListener(comClick);
        }
        private View.OnClickListener likeListener = view -> {
            listener.onPositionClicked(getAdapterPosition());
        };
        private View.OnClickListener comClick = view -> {
            comlistener.onPositionClicked(getAdapterPosition());
        };

    }
}
