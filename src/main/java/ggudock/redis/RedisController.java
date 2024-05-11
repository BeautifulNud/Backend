package ggudock.redis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@Tag(name = "Redis test API", description = "유저 관련 Api (#7)")
@RestController
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, String> redisTemplate;

    @Operation(summary = "레디스 저장 test")
    @PostMapping("/redisTest")
    public ResponseEntity<?> addRedisKey() {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        vop.set("yellow", "banana", 30, TimeUnit.SECONDS);
        vop.set("red", "apple", 30, TimeUnit.SECONDS);
        vop.set("green", "watermelon", 30, TimeUnit.SECONDS);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "레디스에서 키 get")
    @GetMapping("/redisTest/{key}/{email}")
    public ResponseEntity<?> getRedisKey(@PathVariable String key, @PathVariable String email) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

}