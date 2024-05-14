package moyeora.myapp.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisUtil {

  private final RedisTemplate<String,Object> template;
  private final ObjectMapper objectMapper;

  public Object getData(String key) {
    ValueOperations<String, Object> valueOperations = template.opsForValue();
    return valueOperations.get(key);
  }

  public <T> T getData(String key,Class<T> classType) throws JsonProcessingException {
    String redisValue = (String) template.opsForValue().get(key);
    if (ObjectUtils.isEmpty(redisValue)) {
      return null;
    }else{
      return objectMapper.readValue(redisValue,classType);
    }
  }

  public boolean existData(String key) {
    return Boolean.TRUE.equals(template.hasKey(key));
  }

  public void setDataExpire(String key, String value, long duration) {
    ValueOperations<String, Object> valueOperations = template.opsForValue();
    Duration expireDuration = Duration.ofSeconds(duration);
    valueOperations.set(key, value, expireDuration);
  }

  public void setDataExpire(String key,Object classType) throws JsonProcessingException {
    template.opsForValue().set(key, objectMapper.writeValueAsString(classType));
  }

  public void deleteData(String key) {
    template.delete(key);
  }
}
