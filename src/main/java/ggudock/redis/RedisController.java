package ggudock.redis;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Redis API", description = "유저 관련 Api (#7)")
@RestController
@RequiredArgsConstructor
public class RedisController {
    private final RedisTemplate<String, String> redisTemplate;

    @Operation(summary = "refreshToken get")
    @GetMapping("/refreshToken/{email}")
    public ResponseEntity<?> getRefreshToken(@PathVariable(name = "email") String email) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String key = "RT:" + email;
        String value = vop.get(key);
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    @Operation(summary = "로그아웃 확인")
    @GetMapping("/logout")
    public ResponseEntity<Boolean> checkLogout(@RequestParam(name = "accessToken") String accessToken) {
        ValueOperations<String, String> vop = redisTemplate.opsForValue();
        String value = vop.get(accessToken);
        if (value != null)
            return new ResponseEntity<>(true, HttpStatus.OK);
        else
            return new ResponseEntity<>(false, HttpStatus.OK);
    }
}