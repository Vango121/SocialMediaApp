package com.marcel.socialmediaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<String> lista = new ArrayList<>();
    ArrayList<String> listaUID = new ArrayList<>();
    ArrayAdapter adapter;
    Connect connect;
    String str;
    Socket s;
    PrintWriter printWriter;
    FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    public class Connect extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                s = new Socket("10.0.2.2", 2020);
                if(s.isBound()) {
                    Log.i("socket", s.isConnected() + "");
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader br = new BufferedReader(in);
                    printWriter = new PrintWriter(s.getOutputStream());
                    printWriter.println("users");
                    printWriter.flush();

                }else {
                    Log.i("connection","error");
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
        public void checkforupdate(){

            try{
                InputStreamReader in= new InputStreamReader(s.getInputStream());
                BufferedReader br= new BufferedReader(in);

                while (br.ready()){
                    int temp=0;
                    str=br.readLine();
                    listaUID.add(str.substring(0,str.indexOf("name:")));

                    lista.add(str.substring(str.indexOf("name:")+5));

                    Log.i("uid",str.substring(0,str.indexOf("name:")));
                    runOnUiThread(() -> {
                        adapter.notifyDataSetChanged();
                        return;
                    });
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        public void sendData(String data){
            try{
                printWriter = new PrintWriter(s.getOutputStream());
                printWriter.println(data);
                printWriter.flush();
                //Log.i("send",data);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            super.onPostExecute(s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        listView = findViewById(R.id.list);
        adapter = new ArrayAdapter(ContactActivity.this,android.R.layout.simple_list_item_1,lista);
        mAuth = FirebaseAuth.getInstance();
        connect = new Connect();
        try{
            connect.execute();
        }
        catch (Exception e){

        }
        Thread thread = new Thread(){
           @Override
           public void run() {
               int temp=0;
               while(true&&temp<5){
                    connect.checkforupdate();
                   //adapter.notifyDataSetChanged();
               }
           }
       }; thread.start();

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent= new Intent(ContactActivity.this,ChatActivity.class);
            intent.putExtra("Nazwa",lista.get(position));
            intent.putExtra("UID",listaUID.get(position));
            startActivity(intent);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }
}

