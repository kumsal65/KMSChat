package com.kumsal.kmschat;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.friendsViewHolder> {
    private List<FriendModdel> moddelList;
    private Context context;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public interface OnClickItemListener{
        void click(int position);
        void clickOpenProfile(int position);
        void clickSendMessage (int position);
    }
    public FriendsAdapter(List<FriendModdel> moddelList, Context context) {
        this.moddelList = moddelList;
        this.context = context;
    }

    @NonNull
    @Override
    public friendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.user_single,parent,false);
        return new friendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final friendsViewHolder holder, int position) {
        final FriendModdel moddel=moddelList.get(position);
        holder.displayName.setText(moddel.getmDisplayName());
        holder.date.setText(moddel.getDate());
        if (moddel.getImageUrl().equals("") || moddel.getImageUrl().equals(null)){
            moddel.setImageUrl("empty");
        }
        Picasso.get().load(moddel.getImageUrl()).into(holder.imageView);
        if (moddel.getCheckIsOnline().equals("true")){
            holder.checkImage.setImageResource(R.drawable.greentrans);
        }else{
            holder.checkImage.setImageResource(R.drawable.redtrans);
        }
    }

    @Override
    public int getItemCount() {
        return moddelList.size();
    }

    class friendsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        public TextView date,displayName;
        public CircleImageView imageView;

        public CircleImageView checkImage;
        public friendsViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.users_single_status);
            displayName=itemView.findViewById(R.id.user_single_name);
            imageView=itemView.findViewById(R.id.user_single_imageview);
            checkImage=itemView.findViewById(R.id.user_single_onlineimage);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onClickItemListener!=null){
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    onClickItemListener.click(position);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Select");
            MenuItem openProfile=contextMenu.add(Menu.NONE,1,1,"Open Profile");
            MenuItem sendMessage=contextMenu.add(Menu.NONE,2,2,"Send Message");
            openProfile.setOnMenuItemClickListener(this);
            sendMessage.setOnMenuItemClickListener(this);

        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            if (onClickItemListener!=null){
                int position=getAdapterPosition();
                if (position!=RecyclerView.NO_POSITION){
                    switch (menuItem.getItemId()){
                        case 1:
                            onClickItemListener.clickOpenProfile(position);
                            break;
                        case 2:
                            onClickItemListener.clickSendMessage(position);
                            break;
                        default:
                            break;
                    }
                }
            }
            return false;
        }

    }
}
