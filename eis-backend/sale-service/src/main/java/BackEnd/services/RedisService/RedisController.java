package BackEnd.services.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;


    // Save data to Redis
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestParam String key, @RequestParam String value) {
        redisService.save(key, value);
        return ResponseEntity.ok("Data saved successfully!");
    }

    // Get data from Redis
    @GetMapping("/get/{key}")
    public ResponseEntity<String> get(@PathVariable String key) {
        System.out.println("Key: " + key);
        String value = (String) redisService.get(key); // Lấy giá trị chuỗi từ Redis
        if (value == null) {
            System.out.println("Value: null");
            return ResponseEntity.ok("Key not found.");
        }

        // Trả về giá trị chuỗi trực tiếp
        System.out.println("Value: " + value);
        return ResponseEntity.ok(value); // Trả về chuỗi đã lấy
    }


    // Delete data from Redis
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<String> delete(@PathVariable String key) {
        redisService.delete(key);
        return ResponseEntity.ok("Key deleted successfully!");
    }
}

