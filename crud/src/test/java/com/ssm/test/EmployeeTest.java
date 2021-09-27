package com.ssm.test;

import com.ssm.bean.Department;
import com.ssm.bean.Employee;
import com.ssm.dao.DepartmentMapper;
import com.ssm.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * @author LYC
 * @program: crud
 * @description:
 * @date 2021-09-22  15:46:44
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class EmployeeTest {
    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SqlSession sqlSession;

    @Test
    public void test(){
//        Department department1 = new Department(null,"技术部");
//        Department department2 = new Department(null,"安全部");
//        Department department3 = new Department(null,"行政部");
//        departmentMapper.insertSelective(department1);
//        departmentMapper.insertSelective(department2);
//        departmentMapper.insertSelective(department3);

        EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
        for(int i = 0;i<1000;i++){
            String uuid = UUID.randomUUID().toString().substring(0, 8);
            Employee employee = new Employee(null,uuid , "G", uuid+"@qq.com",i%3+1 , null);
            mapper.insertSelective(employee);
        }
    }
}
