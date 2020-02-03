package com.pipecrafts.redisplayaround;

import com.pipecrafts.redisplayaround.config.MethodAwareCacheKeyGenerator;
import com.pipecrafts.redisplayaround.svc.CacheService;
import com.pipecrafts.redisplayaround.svc.ControlledCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@Slf4j
@EnableCaching
@SpringBootApplication
public class RedisPlayAroundApplication implements CommandLineRunner {

  @Autowired
  private CacheService cacheService;

  @Autowired
  private ControlledCacheService controlledCacheService;

  public static void main(String[] args) {
    SpringApplication.run(RedisPlayAroundApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
//    String firstString = cacheService.cacheThis();
//    log.info("First: {}", firstString);
//    String secondString = cacheService.cacheThis();
//    log.info("Second: {}", secondString);


//    log.info("Starting controlled cache: -----------");
//    String controlledFirst = getFromControlledCache();
//    log.info("Controlled First: {}", controlledFirst);
//    String controlledSecond = getFromControlledCache();
//    log.info("Controlled Second: {}", controlledSecond);
//
//    log.info("Clearing all cache entries:");
//
//    controlledCacheService.removeFromCache();
//    String controlledThird = getFromControlledCache();
//    log.info("Controlled third: {}", controlledThird);
    useKeys();
  }

//  private String getFromControlledCache() {
//    String fromCache = controlledCacheService.getFromCache();
//    if (fromCache == null) {
//      log.info("Cache is empty. Populating it.....");
//      String newVal = controlledCacheService.populateCache();
//      log.info("Populated cache with: {}", newVal);
//      return newVal;
//    }
//
//    log.info("Returning from Cache: {}", fromCache);
//    return fromCache;
//  }

  private void useKeys() {
//    String firstString = cacheService.cacheThis("param1", UUID.randomUUID().toString());
//    log.info("First: {}", firstString);
//    String secondString = cacheService.cacheThis("param1", UUID.randomUUID().toString());
//    log.info("Second: {}", secondString);
//    String thirdString = cacheService.cacheThis("AnotherParam", UUID.randomUUID().toString());
//    log.info("Third: {}", thirdString);
//    String fourthString = cacheService.cacheThis("AnotherParam", UUID.randomUUID().toString());
//    log.info("Fourth: {}", fourthString);


    log.info("Starting controlled cache: -----------");
    String controlledFirst = getFromControlledCache("first");
    log.info("Controlled First: {}", controlledFirst);
//    String controlledSecond = getFromControlledCache("second");
//    log.info("Controlled Second: {}", controlledSecond);


    getFromControlledCache("first");
//    getFromControlledCache("second");
//    getFromControlledCache("third");

  }

  private String getFromControlledCache(String param) {
    String fromCache = controlledCacheService.getFromCache(param);
    if (fromCache == null) {
      log.info("Oups - Cache was empty. Going to populate it");
      String newValue = controlledCacheService.populateCache(param);
      log.info("Populated Cache with: {}", newValue);
      return newValue;
    }
    log.info("Returning from Cache: {}", fromCache);
    return fromCache;
  }
}
