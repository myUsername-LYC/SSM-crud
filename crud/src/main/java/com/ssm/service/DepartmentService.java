package com.ssm.service;

import com.ssm.bean.Department;
import com.ssm.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYC
 * @program: crud
 * @description:
 * @date 2021-09-24  09:34:54
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    public List<Department> allDepts() {
        List<Department> departments = departmentMapper.selectByExample(null);
        return departments;
    }
}
