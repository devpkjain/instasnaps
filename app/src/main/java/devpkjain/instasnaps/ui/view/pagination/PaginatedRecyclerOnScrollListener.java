package devpkjain.instasnaps.ui.view.pagination;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

final class PaginatedRecyclerOnScrollListener extends RecyclerView.OnScrollListener {
    private static final int VISIBLE_THRESHOLD = 5;
    private int previousTotal = 0;
    private boolean loading = true;

    private LinearLayoutManager layoutManager;
    private PaginatedDelegate delegate;

    PaginatedRecyclerOnScrollListener(LinearLayoutManager layoutManager, PaginatedDelegate delegate) {
        this.layoutManager = layoutManager;
        this.delegate = delegate;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = recyclerView.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        if (loading && totalItemCount > previousTotal) {
            loading = false;
            previousTotal = totalItemCount;
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            loading = true;
            delegate.loadMore();
        }
    }
}
