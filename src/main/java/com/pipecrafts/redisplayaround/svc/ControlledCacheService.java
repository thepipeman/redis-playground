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


  @Cacheable(cacheNames = "myControlledCache", key = "T(com.pipecrafts.redisplayaround.svc.ControlledCacheService).getCacheKey(#relevant)")
  public String getFromCache(String relevant) {
    return null;
  }

  @CacheEvict(cacheNames = "myControlledCache", key = "T(com.pipecrafts.redisplayaround.svc.ControlledCacheService).getCacheKey(#relevant)")
  public void removeFromCache(String relevant) {
  }

  @CachePut(cacheNames = "myControlledCache", key = "T(com.pipecrafts.redisplayaround.svc.ControlledCacheService).getCacheKey(#relevant)")
  public String populateCache(String relevant, String unrelevantTrackingId) {
    return "this is it again!";
  }
}
