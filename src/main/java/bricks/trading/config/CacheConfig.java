package bricks.trading.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CATEGORY_CACHE = "CATEGORY_CACHE";
    public static final long CATEGORY_TTL = 144;
    public static final long CATEGORY_CACHE_MAX_SIZE = 1;

    @Bean
    public CacheManager cacheManager() {
        List<CaffeineCache> caches = new ArrayList<>();
        caches.add(buildCache(CATEGORY_CACHE, CATEGORY_TTL, TimeUnit.MINUTES, CATEGORY_CACHE_MAX_SIZE));

        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(caches);
        return manager;
    }

    private static CaffeineCache buildCache(String name, long ttl, TimeUnit ttlUnit, long size){
        return new CaffeineCache(name, Caffeine.newBuilder()
                .expireAfterWrite(ttl, ttlUnit)
                .maximumSize(size)
                .build());
    }

}
