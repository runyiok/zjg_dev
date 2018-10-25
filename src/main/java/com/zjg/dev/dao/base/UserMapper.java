package com.zjg.dev.dao.base;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserMapper {

    @Select("select * from adm_user")
    List<Map<String, Object>> query();
}
