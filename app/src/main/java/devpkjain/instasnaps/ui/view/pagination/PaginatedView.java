package devpkjain.instasnaps.ui.view.pagination;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import devpkjain.instasnaps.R;

public final class PaginatedView extends FrameLayout {
    @Bind(R.id.paginated_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.paginated_recycler_view) RecyclerView recyclerView;
    @Bind(R.id.paginated_view_loading) FrameLayout loadingView;

    public PaginatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary);
    }

    public void setDelegate(final PaginatedDelegate delegate) {
        //TODO Expose these later if needed
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        PaginatedAdapter adapter = new PaginatedAdapter(delegate);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginatedRecyclerOnScrollListener(manager, delegate));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override public void onRefresh() {
                delegate.loadNewest();
            }
        });
        if (adapter.getItemCount() > 0) {
            showList();
        } else {
            showLoading();
        }
    }

    public void onDataCompleted() {
        if (recyclerView.getAdapter() != null) {
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
        showList();
    }

    private void showList() {
        isLoading(false);
    }

    private void showLoading() {
        isLoading(true);
    }

    private void isLoading(boolean isLoading) {
        loadingView.setVisibility(isLoading ? VISIBLE : GONE);
        swipeRefreshLayout.setVisibility(isLoading ? GONE : VISIBLE);
        recyclerView.setVisibility(isLoading ? GONE : VISIBLE);
    }
}
