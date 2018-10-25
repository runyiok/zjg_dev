package com.zjg.dev.dao.log;

import com.zjg.dev.domain.CustSysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SysLogMapper {

    @Select("select * from adm_user_login_log limit 10")
    List<Map<String, Object>> query();


    @Insert({"insert into t_log_operation(opId, userName, operation, params, method,ip,createTime) " +
            " values(#{opId}, #{userName}, #{operation}, #{params},#{method}.#{ip}, now())"})
    void insert(CustSysLog log);
}
