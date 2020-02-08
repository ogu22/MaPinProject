package myapplication.example.mapinproject.business;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import myapplication.example.mapinproject.R;
import myapplication.example.mapinproject.data.entities.Reply;

public class ReplyRecycleViewAdapter extends RecyclerView.Adapter<ReplyViewHolder> {
    private List<Reply> list;

    public ReplyRecycleViewAdapter(List<Reply> list) {
        if (list == null) {
            this.list = new ArrayList<>();
            return;
        }
        this.list = list;
    }

    @Override
    public ReplyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.reply_row, parent,false);
        ReplyViewHolder vh = new ReplyViewHolder(inflate);
        return vh;
    }

    @Override
    public void onBindViewHolder(ReplyViewHolder holder, int position) {
        new DownloadImageTask(holder.imageView).execute(list.get(position).getReplayUserImagePath());
        holder.replyDate.setText(list.get(position).getReplayDate());
        holder.detailView.setText(list.get(position).getContent());
        holder.titleView.setText(list.get(position).getReplayUserName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
