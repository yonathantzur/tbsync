import sync.redis.RedisClient;
import sync.RefreshCache;

import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> addresses = Arrays.asList(
                "redis://1.1.1.1:26379",
                "redis://2.2.2.2:26379",
                "redis://3.3.3.3:26379");
        RedisClient.getInstance().initConnection(addresses,
                "password",
                "redismaster");
        RefreshCache refreshCache = new RefreshCache();

        refreshCache.subscribe(() -> {
            // Calling the refresh cache method...
            System.out.println("hello");
        });

        // Create refresh cache for all subscribers.
        refreshCache.publish();
    }
}
