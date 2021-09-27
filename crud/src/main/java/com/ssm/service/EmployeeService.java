package com.ssm.service;

import com.ssm.bean.Employee;
import com.ssm.bean.EmployeeExample;
import com.ssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author LYC
 * @program: crud
 * @description:
 * @date 2021-09-22  16:51:19
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public List<Employee> getAll() {
        return employeeMapper.selectByExampleWithDept(null);
    }

    public boolean addEmployee(Employee employee) {
        int i = employeeMapper.insertSelective(employee);
        if (i>0){
            return true;
        }
        return false;
    }

    public boolean isEmpNameExist(String empName) {
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andEmpNameEqualTo(empName);
        List<Employee> employees = employeeMapper.selectByExample(example);
        return employees.size()>0;
    }

    public boolean isEmailExist(String email) {
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andEmailEqualTo(email);
        List<Employee> employees = employeeMapper.selectByExample(example);
        return employees.size()>0;
    }

    public Employee getEmpByEmpId(int empId) {
        return employeeMapper.selectByPrimaryKeyWithDept(empId);
    }

    public boolean deleteEmpByEmpId(int empId) {
        int i = employeeMapper.deleteByPrimaryKey(empId);
        return i>0;
    }

    public boolean updateEmp(Employee employee) {
        int i = employeeMapper.updateByPrimaryKey(employee);
        return i>0;
    }
}
