package com.zjg.dev.service.impl;

import com.zjg.dev.dao.log.SysLogMapper;
import com.zjg.dev.service.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysLogServiceImpl implements ISysLogService {
    @Autowired
    private SysLogMapper mapper;

    @Override
    public List<Map<String, Object>> queryUser() {
        return mapper.query();
    }
}
