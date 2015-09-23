package devpkjain.instasnaps.api;

import devpkjain.instasnaps.api.internal.RealPopularSnapApi;

public class InstaApi {
    private static final PopularSnapApi popularSnapApi = new RealPopularSnapApi();

    public static PopularSnapApi getInstaSnapApi() {
        return popularSnapApi;
    }
}

