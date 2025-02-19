package BackEnd.services.RedisService;

public interface IRedisService {

    void save(String key, Object value);

    void saveWithExpiration(String key, Object value, long timeout);

    Object get(String key);

    void delete(String key);
}

