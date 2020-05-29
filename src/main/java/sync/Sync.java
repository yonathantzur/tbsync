package sync;

import sync.redis.RedisClient;

public abstract class Sync {
    private String topicName;
    private String message;

    Sync(String topicName, String message) {
        this.topicName = topicName;
        this.message = message;

        if (!RedisClient.getInstance().isConnected()) {
            throw new RuntimeException("RedisClient initConnection method should be called");
        }
    }

    public void publish() {
        RedisClient.getInstance().publish(this.topicName, this.message);
    }

    public void subscribe(Runnable callback) {
        RedisClient.getInstance().subscribe(this.topicName, this.message, callback);
    }
}
