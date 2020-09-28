package com.kumsal.kmschat;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdaptar extends RecyclerView.Adapter<UsersAdaptar.UserViewHolder> {

    Context context;
    List<Users> userValue;
    public UsersAdaptar(Context context, List<Users> userValue) {
        this.context = context;
        this.userValue = userValue;
    }


    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_single,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        Users users=userValue.get(position);
        holder.status.setText(users.getStatus());
        holder.name.setText(users.getName());
        Picasso.get().load(users.getImage()).into(holder.imageView);
        Uri uri=Uri.parse("https://firebasestorage.googleapis.com/v0/b/kmschat-b443e.appspot.com/o/profile_images%2Fprofile_imagexWsJuFFuSvg5DtyrOCgUvSf0LCy2.jpeg?alt=media&token=0447cef8-ca81-46b4-8d1b-c6a7af6a4d08");
        holder.imageView.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return userValue.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder{

        public CircleImageView imageView;
        public TextView name,status;
       public UserViewHolder(@NonNull View itemView) {
           super(itemView);
           imageView=itemView.findViewById(R.id.user_single_imageview);
           name=itemView.findViewById(R.id.user_single_name);
           status=itemView.findViewById(R.id.users_single_status);
       }
   }
}
