package com.kumsal.kmschat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

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
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        UI=getIntent().getStringExtra("ui");
        mRefUsers= FirebaseDatabase.getInstance().getReference("Users");
        toolbar=findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        WaitDialog.show(this,"Please Wait");
        imageView=findViewById(R.id.circle_app_toolbar_dark);
        textView=findViewById(R.id.textview_app_toolbar_dark);
        mRefUsers.child(UI).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name=snapshot.child("name").getValue().toString();
                String image=snapshot.child("thumbalimage").getValue().toString();
                if (image.equals("") || image.equals(null)){
                    image="emty";
                }
                textView.setText(name);
                Picasso.get().load(image).into(imageView);
                WaitDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}