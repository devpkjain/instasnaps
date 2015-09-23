package devpkjain.instasnaps.api.internal;

import android.support.annotation.Nullable;
import devpkjain.instasnaps.api.vo.InstaResponse;
import retrofit.RestAdapter;
import rx.Observable;

public class RealPopularSnapsService {
    private static final String API_CLIENT_ID = "fb88fad8d88f42ca8e59ac9a6b7c969a";
    private static final String API_BASE_URL = "https://api.instagram.com";
    private static final String API_POPULAR_URL = "/v1/media/popular";
    private static final int PAGE_SIZE = 10;
    private final PopularSnapsService popularSnapsService;

    public RealPopularSnapsService() {
        popularSnapsService =
                new RestAdapter.Builder().setEndpoint(API_BASE_URL).setLogLevel(RestAdapter.LogLevel.FULL).build().create(PopularSnapsService.class);
    }

    public static RealPopularSnapsService create() {
        return new RealPopularSnapsService();
    }

    Observable<InstaResponse> getPopularSnaps(@Nullable String next_max_id) {
        return popularSnapsService.getPopularSnaps(API_CLIENT_ID, next_max_id, PAGE_SIZE);
    }
}
