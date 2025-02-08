package org.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;
import org.example.mapper.StudentMapper;
import org.example.pojo.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private StudentMapper studentMapper;

    private Gson gson = new Gson();
    private ObjectMapper objectMapper = new ObjectMapper();
    // 存储数据
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    // 获取数据
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void setValueWithExpiration(String key, Object value, long timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }
    // 删除数据
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    public void syncData(String redisKey){
        Object studentObj = redisTemplate.opsForValue().get(redisKey);
        if(studentObj != null){
            String studentStr = (String) studentObj;
            // 将 JSON 字符串转换为 Student 对象
            //方式一（通过gson转换）
            Student student = gson.fromJson(studentStr, Student.class);
            studentMapper.insert(student);
            redisTemplate.delete(redisKey);
            //方式二（通过objectMapper转换）
            /*try {
                Student student = objectMapper.readValue(studentStr, Student.class);
                studentMapper.insert(student);
                redisTemplate.delete(redisKey);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }*/

        }
    }

    public void testRedis(){
        //字符串操作
        // 设置键值
        redisTemplate.opsForValue().set("key01","value01");
        // 获取键值
        String value= (String) redisTemplate.opsForValue().get("key01");
        System.out.println("存储字符串："+value);
        // 删除键
        redisTemplate.delete("key01");

        //Hash操作
        // 设置 Hash
        redisTemplate.opsForHash().put("myHash","field1","value1");
        redisTemplate.opsForHash().put("myHash","field2","value2");
        // 获取 Hash 中的字段
        String fieldValue= (String) redisTemplate.opsForHash().get("myHash","field2");
        System.out.println("hash数据："+fieldValue);
        // 获取整个 Hash
        Map<Object,Object> hashEntries = redisTemplate.opsForHash().entries("myHash");
        System.out.println("全部hash数据："+hashEntries);
        // 删除 Hash 中的字段
        redisTemplate.opsForHash().delete("myHash","field1");

        //列表操作
        // 添加元素到列表
        redisTemplate.opsForList().leftPush("myList","value1");
        redisTemplate.opsForList().leftPush("myList","value2");
        redisTemplate.opsForList().rightPush("myList","value3");
        // 获取列表元素
        String listValue= (String) redisTemplate.opsForList().index("myList",0);
        System.out.println("列表中第一个数据："+listValue);
        // 获取列表全部元素
        List<Object> listValues=redisTemplate.opsForList().range("myList",0,-1);
        System.out.println("全部列表数据："+listValues);
        // 删除列表中的元素
        redisTemplate.opsForList().remove("myList",2,"value2");//count:0 全部，+n 从左数n个，-n 从右数n个
        List<Object> listValues2=redisTemplate.opsForList().range("myList",0,-1);
        System.out.println("全部列表数据（删除后）："+listValues2);

        //集合操作
        // 添加元素到集合
        redisTemplate.opsForSet().add("mySet","value1");
        redisTemplate.opsForSet().add("mySet","value2");
        redisTemplate.opsForSet().add("mySet","value3");
        // 获取集合中的所有元素
        Set<Object> setValues=redisTemplate.opsForSet().members("mySet");
        System.out.println("集合中的数据："+setValues);
        // 删除集合中的元素
        redisTemplate.opsForSet().remove("mySet","value1");

        //有序集合操作
        // 添加元素到有序集合
        redisTemplate.opsForZSet().add("myZSet","value1",4);
        redisTemplate.opsForZSet().add("myZSet","value2",2);
        redisTemplate.opsForZSet().add("myZSet","value3",3);
        // 获取有序集合中的所有元素
        Set<Object> zsetValues=redisTemplate.opsForZSet().range("myZSet",0,-1);
        System.out.println("有序集合的数据："+zsetValues);
        Set<Object> zsetValuesDesc=redisTemplate.opsForZSet().reverseRange("myZSet",0,-1);
        System.out.println("有序集合的数据（倒序）："+zsetValuesDesc);
        // 删除有序集合中的元素
        String[] elementsToRemove={"value1", "value2"};
        redisTemplate.opsForZSet().remove("myZSet", elementsToRemove);
        Set<Object> zsetValues2=redisTemplate.opsForZSet().range("myZSet",0,-1);
        System.out.println("有序集合的数据（删除后）："+zsetValues2);

        //事务操作
        /*使用场景
        原子性保证：
        需要确保多个操作要么全部成功，要么全部失败。例如，更新账户余额和设置账户状态，一旦其中一个操作失败，就需要回退所有操作。
        一致性：
        在多个相关的命令执行时，确保它们之间有一致的状态。例如，一个操作需要消耗某个资源，并便于检查其他资源的现状。
        批量操作：
        当你有一组操作需要批量执行时，可以通过事务将这些操作打包。
        并发控制：
        在高并发环境中，某些操作可能会引起数据的不一致，使用 Redis 事务可以避免这种情况。*/
        List<Object> multiResults=redisTemplate.execute(new SessionCallback<List<Object>>() {
            @Override
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                // 开始事务
                operations.multi();
                // 执行一系列操作
                operations.opsForValue().set("key1","val1");
                operations.opsForValue().set("key2","val2");
                // 提交事务
                return operations.exec();
            }
        });
        System.out.println("事务："+multiResults);
    }





}
