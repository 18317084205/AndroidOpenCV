/*
 * Copyright 2017 Zhihu Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.liang.media;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount;
    private int dividerWidth;

    public RecyclerItemDecoration(int spanCount, int dividerWidth) {
        this.spanCount = spanCount;
        this.dividerWidth = dividerWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager == null) {
            super.getItemOffsets(outRect, view, parent, state);
            return;
        }

        int position = parent.getChildAdapterPosition(view);

        if (layoutManager instanceof GridLayoutManager) {
            int column = position % spanCount;
            outRect.left = dividerWidth - column * dividerWidth / spanCount;
            outRect.right = (column + 1) * dividerWidth / spanCount;
            if (position < spanCount) {
                outRect.top = dividerWidth;
            }
            outRect.bottom = dividerWidth;
        } else if (layoutManager instanceof LinearLayoutManager) {
            if (((LinearLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.HORIZONTAL) {
                outRect.left = position > 0 ? dividerWidth / 2 : dividerWidth;
                outRect.right = position < parent.getAdapter().getItemCount() - 1 ? dividerWidth / 2 : dividerWidth;
            } else {
                outRect.top = position > 0 ? dividerWidth / 2 : dividerWidth;
                outRect.bottom = position < parent.getAdapter().getItemCount() - 1 ? dividerWidth / 2 : dividerWidth;
            }
        }

    }
}
