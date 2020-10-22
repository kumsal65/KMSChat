package com.kumsal.kmschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kongzue.dialog.v3.WaitDialog;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private String UI;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DatabaseReference mRefUsers;
    private CircleImageView imageView;
    private TextView textView,onlineText;

    private FirebaseAuth mAuth;
    private String clickUserId;
    private String ownUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth=FirebaseAuth.getInstance();
        ownUserId=mAuth.getUid();
        clickUserId=getIntent().getStringExtra("ui");

        setContentView(R.layout.activity_chat);
        UI=getIntent().getStringExtra("ui");
        mRefUsers= FirebaseDatabase.getInstance().getReference("Users");
        toolbar=findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar=getSupportActionBar();
        getSupportActionBar().setTitle("");
        WaitDialog.show(this,"Please Wait");
        imageView=findViewById(R.id.chat_custom_imageview);
        textView=findViewById(R.id.chat_custom_name);
        onlineText=findViewById(R.id.chat_custom_online);
        String name=getIntent().getStringExtra("un");
        String image=getIntent().getStringExtra("iu");
        if (image.equals("") || image.equals(null)){
            image="emty";
        }
        textView.setText(name);
        mRefUsers.child(getIntent().getStringExtra("ui")).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String online=snapshot.child("online").getValue()+"";
                String lastSeen=snapshot.child("lastSeen").getValue()+"";
                if (online.equals("true")){
                    onlineText.setText("Online");
                }
                else{
                    getTimeAgo timeAgo=new getTimeAgo();
                    Long lastTime=Long.parseLong(lastSeen);
                    String lastSeenTime=getTimeAgo.getTimeAgo2(lastTime,getApplicationContext());
                    onlineText.setText(lastSeenTime);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Picasso.get().load(image).into(imageView);
        WaitDialog.dismiss();

        LayoutInflater inflater= (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View ation_bar_view=inflater.inflate(R.layout.chat_custom_bar,null);
        actionBar.setCustomView(ation_bar_view);

    }
}