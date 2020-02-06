package myapplication.example.mapinproject.business;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import myapplication.example.mapinproject.R;

public class ReplyItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public ReplyItemDecoration(int space) {
        this.space = space;
    }

    public static ReplyItemDecoration createDefaultDecoration(Context context) {

        int spacingInPixels = context.getResources().getDimensionPixelSize(R.dimen.layout_margin_4);
        return new ReplyItemDecoration(spacingInPixels);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.top = space;
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

    }
}
