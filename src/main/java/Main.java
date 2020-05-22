import redis.RedisClient;

public class Main {
    public static void main(String[] args) {
        RedisClient.getInstance().initConnection("redis://127.0.0.1:6379", "password");
        RefreshCache refreshCache = new RefreshCache();

        refreshCache.subscribe(() -> {
            // Calling the refresh cache method...
            System.out.println("hello");
        });

        // Create refresh cache for all subscribers.
        refreshCache.publish();
    }
}
