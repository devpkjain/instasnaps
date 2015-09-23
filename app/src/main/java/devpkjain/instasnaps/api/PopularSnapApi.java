package devpkjain.instasnaps.api;

import android.support.annotation.Nullable;
import devpkjain.instasnaps.api.vo.InstaResponse;
import rx.Observable;

public interface PopularSnapApi {
    public Observable<InstaResponse> getPopularSnaps(@Nullable String next_max_id);
}