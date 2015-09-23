package devpkjain.instasnaps.api.internal;

import android.support.annotation.Nullable;
import devpkjain.instasnaps.api.vo.InstaResponse;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface PopularSnapsService {
    @GET("/v1/media/popular") Observable<InstaResponse> getPopularSnaps(@Query("client_id") String clientId, @Nullable @Query("next_max_id") String next_max_id,
            @Query("count") int count);
}