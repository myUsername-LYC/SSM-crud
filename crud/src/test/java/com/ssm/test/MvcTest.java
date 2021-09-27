package com.ssm.test;

import com.github.pagehelper.PageInfo;
import com.ssm.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author LYC
 * @program: crud
 * @description:
 * @date 2021-09-22  17:18:16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:springMVC.xml"})
public class MvcTest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    @Before
    public void initMokcMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5"))
                .andReturn();
        HttpServletRequest request = result.getRequest();
        PageInfo pageInfo = (PageInfo) request.getAttribute("pageInfo");
        System.out.println("当前页数="+pageInfo.getPageNum());
        System.out.println("总页数=" + pageInfo.getPages());
        System.out.println("总记录数=" + pageInfo.getTotal());
        int[] nums = pageInfo.getNavigatepageNums();
        for (int i = 0; i < nums.length; i++) {
            System.out.print(" "+nums[i]);
        }
        System.out.println();
        List<Employee> list = pageInfo.getList();
        for (Employee emp : list) {
            System.out.println(emp);
        }

    }
}
