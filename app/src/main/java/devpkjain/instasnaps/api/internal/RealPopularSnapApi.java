package devpkjain.instasnaps.api.internal;

import android.support.annotation.Nullable;
import devpkjain.instasnaps.api.PopularSnapApi;
import devpkjain.instasnaps.api.vo.InstaResponse;
import rx.Observable;

public class RealPopularSnapApi implements PopularSnapApi {

    private static final RealPopularSnapsService popularSnapsService = RealPopularSnapsService.create();

    @Override public Observable<InstaResponse> getPopularSnaps(@Nullable String next_max_id) {
        return popularSnapsService.getPopularSnaps(next_max_id);
    }
}
