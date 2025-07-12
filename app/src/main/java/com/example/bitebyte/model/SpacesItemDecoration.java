package com.example.bitebyte.model;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    private final boolean addSpaceTopBottom;

    public SpacesItemDecoration(int space, boolean addSpaceTopBottom) {
        this.space = space;
        this.addSpaceTopBottom = addSpaceTopBottom;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = state.getItemCount();

        if (addSpaceTopBottom) {
            outRect.top = space / 2;
            outRect.bottom = space / 2;

            if (position == 0) {
                outRect.top = space;
            }
            if (position == itemCount - 1) {
                outRect.bottom = space;
            }
        } else {
            outRect.bottom = space;
        }
    }
}
