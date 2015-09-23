package devpkjain.instasnaps.ui.view.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import devpkjain.instasnaps.R;
import devpkjain.instasnaps.ui.view.MaterialProgressView;

final class PaginatedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int PROGRESS_VIEW_TYPE = 99;
    private final PaginatedDelegate delegate;

    PaginatedAdapter(PaginatedDelegate delegate) {
        this.delegate = delegate;
    }

    @Override public int getItemViewType(int position) {
        if (position == delegate.getItemCount()) {
            return PROGRESS_VIEW_TYPE;
        }
        return delegate.getItemViewType(position);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == PROGRESS_VIEW_TYPE) {
            return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.paginated_progress_item, parent, false));
        }
        return delegate.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ProgressViewHolder) {
            //No op
            return;
        }
        //Note: if there is a cast exception it's on the caller.
        //noinspection unchecked
        delegate.onBindViewHolder(holder, position);
    }

    @Override public int getItemCount() {
        if (delegate.getItemCount() == 0) {
            return 0;
        }
        if (!delegate.hasMoreToLoad()) {
            return delegate.getItemCount();
        }
        return delegate.getItemCount() + 1;
    }

    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.progress) MaterialProgressView paginatedView;

        public ProgressViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}