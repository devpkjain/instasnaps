package devpkjain.instasnaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import butterknife.Bind;
import butterknife.ButterKnife;
import devpkjain.instasnaps.api.InstaApi;
import devpkjain.instasnaps.api.vo.InstaResponse;
import devpkjain.instasnaps.api.vo.Media;
import devpkjain.instasnaps.ui.view.MediaViewHolder;
import devpkjain.instasnaps.ui.view.pagination.PaginatedDelegate;
import devpkjain.instasnaps.ui.view.pagination.PaginatedView;
import java.util.ArrayList;
import java.util.List;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class SnapsActivity extends AppCompatActivity implements PaginatedDelegate<MediaViewHolder> {
    @Bind(R.id.paginated_view) PaginatedView paginatedView;
    @Bind(R.id.main_tool_bar) Toolbar toolbar;
    private List<Media> medias;

    private CompositeSubscription compSub = new CompositeSubscription();
    private String next_max_id;
    private boolean paginationOverride = true;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snaps);
        ButterKnife.bind(this);
        initToolbar();
        init();
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void initToolbar() {
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha));
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        toolbar.setTitle("Insta Snaps");
    }

    private void init() {
        medias = new ArrayList<Media>();
        paginatedView.setBackgroundColor(getResources().getColor(android.R.color.white));
        paginatedView.setDelegate(this);
        loadNewest();
    }

    private void load(String next_max_id) {
        compSub.add(InstaApi.getInstaSnapApi().getPopularSnaps(next_max_id).observeOn(AndroidSchedulers.mainThread()).finallyDo(new Action0() {
            @Override public void call() {
                if (paginatedView != null && medias.size() > 0) {
                    paginatedView.onDataCompleted();
                }
            }
        }).subscribe(new Action1<InstaResponse>() {
            @Override public void call(InstaResponse instaResponse) {
                if (instaResponse != null && instaResponse.pagination != null) {
                    updateNextMaxId(instaResponse.pagination.next_max_id);
                }
                handleData(instaResponse.data);
            }
        }, new Action1<Throwable>() {
            @Override public void call(Throwable throwable) {
                Log.d("Throwable", throwable.toString());
            }
        }));
    }

    private void updateNextMaxId(String next_max_id) {
        this.next_max_id = next_max_id;
    }

    public <T> void handleData(List<T> data) {
        for (T item : data) {
            medias.add((Media) item);
        }
    }

    @Override public void loadNewest() {
        medias.clear();
        updateNextMaxId(null);
        load(next_max_id);
    }

    @Override public void loadMore() {
        if (hasMoreToLoad()) {
            load(next_max_id);
        }
    }

    @Override public boolean hasMoreToLoad() {
        return next_max_id != null || paginationOverride;
    }

    @Override public int getItemViewType(int position) {
        return 0;
    }

    @Override public int getItemCount() {
        return medias.size();
    }

    @Override public MediaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MediaViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_photo, parent, false));
    }

    @Override public void onBindViewHolder(MediaViewHolder holder, int position) {
        holder.bind(medias.get(position));
    }
}
