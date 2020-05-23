package redis;

import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;

import java.util.List;

public class RedisClient {
    private static volatile RedisClient instance = null;
    private RedissonClient redis;

    private RedisClient() {
    }

    public static RedisClient getInstance() {
        if (instance == null) {
            synchronized (RedisClient.class) {
                if (instance == null) {
                    instance = new RedisClient();
                }
            }
        }

        return instance;
    }

    // Init single.
    public void initConnection(String address, String password) {
        Config config = new Config();
        config.useSingleServer().setAddress(address).setPassword(password);

        this.redis = Redisson.create(config);
    }

    // Init sentinel.
    public void initConnection(List<String> addresses, String password, String masterName) {
        Config config = new Config();
        SentinelServersConfig sentinelConf = config.useSentinelServers()
                .setMasterName(masterName).setPassword(password);
        addresses.forEach(sentinelConf::addSentinelAddress);

        this.redis = Redisson.create(config);
    }

    public boolean isConnected() {
        return this.redis != null;
    }

    public void publish(String topicName, String message) {
        RTopic topic = this.redis.getTopic(topicName);
        topic.publish(message);
    }

    public void subscribe(String topicName, String message, Runnable callback) {
        RTopic topic = this.redis.getTopic(topicName);
        topic.addListener(String.class, (charSequence, str) -> {
            if (str.equals(message)) {
                callback.run();
            }
        });
    }

}
