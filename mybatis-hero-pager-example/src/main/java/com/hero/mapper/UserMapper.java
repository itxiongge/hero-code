package com.hero.mapper;

import com.hero.Utils.HeroPager;
import com.hero.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    @Select("select * from t_user")
    List<User> getUserPageable(HeroPager pager);

    @Insert("insert into t_user (id,name,age,address) values (#{id},#{name},#{age},#{address});")    void save(User user);

    @Delete("delete from t_user")
    void deleteAll();

}
