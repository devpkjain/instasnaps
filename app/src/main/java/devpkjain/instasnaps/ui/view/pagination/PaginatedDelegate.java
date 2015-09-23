package devpkjain.instasnaps.ui.view.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public interface PaginatedDelegate<H extends RecyclerView.ViewHolder> {
    void loadNewest();

    void loadMore();

    boolean hasMoreToLoad();

    int getItemViewType(int position);

    int getItemCount();

    H onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(H holder, int position);
}
