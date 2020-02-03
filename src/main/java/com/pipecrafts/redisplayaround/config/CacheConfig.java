package com.pipecrafts.redisplayaround.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableConfigurationProperties(CacheConfigProperties.class)
public class CacheConfig extends CachingConfigurerSupport {

  @Override
  public KeyGenerator keyGenerator() {
    return new MethodAwareCacheKeyGenerator();
  }


  private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
    return RedisCacheConfiguration.defaultCacheConfig()
      .entryTtl(Duration.ofSeconds(timeoutInSeconds));
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory(CacheConfigProperties properties) {
    log.info("Redis (/Lettuce) configuration enabled. With cache timeout " + properties.getTimeoutSeconds() + " seconds.");

    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(properties.getRedisHost());
    redisStandaloneConfiguration.setPort(properties.getRedisPort());
    return new LettuceConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
    RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
    redisTemplate.setConnectionFactory(cf);
    return redisTemplate;
  }

  @Bean
  public RedisCacheConfiguration cacheConfiguration(CacheConfigProperties properties) {
    return createCacheConfiguration(properties.getTimeoutSeconds());
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, CacheConfigProperties properties) {
    Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

    for (Map.Entry<String, Long> cacheNameAndTimeout : properties.getCacheExpirations().entrySet()) {
      cacheConfigurations.put(cacheNameAndTimeout.getKey(), createCacheConfiguration(cacheNameAndTimeout.getValue()));
    }

    RedisCacheManager cacheManager = RedisCacheManager
      .builder(redisConnectionFactory)
      .cacheDefaults(cacheConfiguration(properties))
      .withInitialCacheConfigurations(cacheConfigurations)
      .build();


    return cacheManager;
  }

}
