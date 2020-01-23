package com.pipecrafts.redisplayaround.svc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CacheService {

  @Cacheable(cacheNames = "myCache")
  public String cacheThis() {
    log.info("Returning NOT from cache!");
    return "this Is it";
  }

  @Cacheable(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
  public String cacheThis(String relevant, String unrelevantTrackingId){
    log.info("Returning NOT from cache. Tracking: {}!", unrelevantTrackingId);
    return "this Is it";
  }

  @CacheEvict(cacheNames = "myCache", key = "'myPrefix_'.concat(#relevant)")
  public void forgetAboutThis(String relevant){
    log.info("Forgetting everything about this '{}'!", relevant);
  }

}
