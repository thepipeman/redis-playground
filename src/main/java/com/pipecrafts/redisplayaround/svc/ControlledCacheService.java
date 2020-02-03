package com.pipecrafts.redisplayaround.svc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ControlledCacheService {

  private static final String CONTROLLED_PREFIX = "myControlledPrefix_";

  public static String getCacheKey(String relevant){
    return CONTROLLED_PREFIX + relevant;
  }

  private static final String CACHE_REGION = "__myControlledCache";


  @Cacheable(cacheNames = CACHE_REGION, key = "T(com.pipecrafts.redisplayaround.svc.ControlledCacheService).getCacheKey(#relevant)")
  public String getFromCache(String relevant) {
    return null;
  }

  @CacheEvict(cacheNames = CACHE_REGION, key = "T(com.pipecrafts.redisplayaround.svc.ControlledCacheService).getCacheKey(#relevant)")
  public void removeFromCache(String relevant) {
  }

  @CachePut(cacheNames = CACHE_REGION, key = "T(com.pipecrafts.redisplayaround.svc.ControlledCacheService).getCacheKey(#relevant)")
  public String populateCache(String relevant) {
    return "this is it again!";
  }
}
