package myapplication.example.mapinproject.business;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.example.mapinproject.R;

public class ReplyViewHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView titleView;
    public TextView detailView;
    public TextView replyDate;

    public ReplyViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.reply_user_image);
        titleView = itemView.findViewById(R.id.reply_user_name);
        detailView = itemView.findViewById(R.id.reply_message);
        replyDate = itemView.findViewById(R.id.reply_date);
    }
}
