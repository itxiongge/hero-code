package com.hero.service;

import com.hero.bean.Employee;
import com.hero.dao.EmployeeDao;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service  // Dubbo的注解  <dubbo:service/>
@Component
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeDao dao;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    // 当有对象插入时会清空realTimeCache缓存空间
    @CacheEvict(value="realTimeCache", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addEmployee(Employee employee) {
        dao.insertEmployee(employee);
    }

    // 一旦有了查询结果，则会将此结果写入到realTimeCache缓存
    // key是employee_加上方法参数
    @Cacheable(value="realTimeCache", key = "'employee_'+#id")
    @Override
    public Employee findEmployeeById(int id) {
        // 从DB查询
        System.out.println("从DB查询id = " + id);
        return dao.selectEmployeeById(id);
    }

    private volatile Object count;
    // 双重检测锁机制解决Reids的热点缓存问题
    @Override
    public Integer findEmployeeCount() {
        // 获取Redis操作对象
        BoundValueOperations<Object, Object> ops = redisTemplate.boundValueOps("count");
        // 从缓存获取数据
        count = ops.get();
        if(count == null) {//为什么要使用双重检查锁呢？Spring中的单例对象也会用到双重检查锁
           synchronized (this) {
                count = ops.get();
                if(count == null) {
                    System.out.println("从DB中查询");
                    // 从DB中查询
                    count = dao.selectEmployeeCount();
                    // 将查询结果存放到Redis，有效时间10s
                    ops.set(count, 100, TimeUnit.SECONDS);
                }
            }
        }
        return (Integer) count;
    }
}
