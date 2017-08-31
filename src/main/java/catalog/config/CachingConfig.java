package catalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class CachingConfig {

    private Environment env;

    @Autowired
    CachingConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {
        RedisCacheManager cm =  new RedisCacheManager(redisTemplate);
        cm.setUsePrefix(true);
        return cm;
    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory =
            new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(env.getProperty("redis.host-name", "localhost"));
        jedisConnectionFactory.setPort(Integer.valueOf(env.getProperty("redis.port", "6379")));
        jedisConnectionFactory.setUsePool(true);

        jedisConnectionFactory.afterPropertiesSet();
        return jedisConnectionFactory;
    }
    @Bean
    public RedisTemplate<String, String> redisTemplate(
        RedisConnectionFactory redisCF) {
        RedisTemplate<String, String> redisTemplate =
            new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(redisCF);
        //redisTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
