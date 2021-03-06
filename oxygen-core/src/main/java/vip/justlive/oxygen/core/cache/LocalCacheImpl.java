/*
 * Copyright (C) 2018 justlive1
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package vip.justlive.oxygen.core.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import vip.justlive.oxygen.core.util.ExpiringMap;

/**
 * 本地缓存实现
 *
 * @author wubo
 */
public class LocalCacheImpl implements Cache {

  private final ExpiringMap<String, Object> expiringMap = ExpiringMap.create();

  @Override
  public Object get(String key) {
    return expiringMap.get(key);
  }

  @Override
  public <T> T get(String key, Class<T> clazz) {
    return clazz.cast(get(key));
  }

  @Override
  public Map<String, Object> get(String... keys) {
    Map<String, Object> map = new HashMap<>(8);
    for (String key : keys) {
      map.put(key, get(key));
    }
    return map;
  }

  @Override
  public Object putIfAbsent(String key, Object value) {
    return expiringMap.putIfAbsent(key, value);
  }

  @Override
  public Object putIfAbsent(String key, Object value, long duration, TimeUnit unit) {
    if (!expiringMap.containsKey(key)) {
      expiringMap.put(key, value, duration, unit);
      return null;
    }
    return expiringMap.get(key);
  }

  @Override
  public Object set(String key, Object value) {
    return expiringMap.put(key, value);
  }

  @Override
  public Object set(String key, Object value, long duration, TimeUnit unit) {
    return expiringMap.put(key, value, duration, unit);
  }

  @Override
  public Object replace(String key, Object value) {
    return expiringMap.replace(key, value);
  }

  @Override
  public Object replace(String key, Object value, long duration, TimeUnit unit) {
    return expiringMap.replace(key, value, duration, unit);
  }

  @Override
  public Collection<String> keys() {
    return expiringMap.keySet();
  }

  @Override
  public void remove(String... keys) {
    for (String key : keys) {
      expiringMap.remove(key);
    }
  }

  @Override
  public void clear() {
    expiringMap.clear();
  }
}
