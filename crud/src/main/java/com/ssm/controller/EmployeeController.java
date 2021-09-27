package com.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ssm.bean.Department;
import com.ssm.bean.Employee;
import com.ssm.bean.Msg;
import com.ssm.service.DepartmentService;
import com.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LYC
 * @program: crud
 * @description:
 * @date 2021-09-22  16:50:24
 */
@Controller
@Validated
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;


    //返回json格式返回全部员工的信息
    //使用ResponseBody要有getter和setter
    @RequestMapping(value = {"/emps/{pn}","/emps"},method = RequestMethod.GET)

    @ResponseBody()
    public Msg getEmpsWithJson(@PathVariable(value = "pn",required = false) Integer pn, Model model){
        if (pn==null){
            pn=1;
        }
        PageHelper.startPage(pn, 5);
        List<Employee> employees = employeeService.getAll();
        PageInfo<Employee> pageInfo = new PageInfo<>(employees,5);
        model.addAttribute("pageInfo", pageInfo);
        Msg msg = new Msg();
        return msg.success().add("pageInfo",pageInfo );
    }

    //返回所有的部门信息
    @RequestMapping(value = "/depts")
    @ResponseBody
    public Msg getDepts(){
        List<Department> departments = departmentService.allDepts();
        Msg msg = new Msg();
       return msg.success().add("depts",departments );
    }

    //添加员工信息,返回添加结果
    @RequestMapping(value = "/emp",method = RequestMethod.POST)
    @ResponseBody
     public Msg addEmp(@Valid Employee employee, BindingResult result){
        Msg msg = new Msg();
        if (result.hasErrors()) {
            //校验失败
            msg.fail();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                msg.add(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }else{
            //校验成功
            if (employeeService.addEmployee(employee)){
                msg.success();
            }else {
                msg.fail().add("fail", "添加失败!");
            }
        }
        return msg;
    }

    //校验empName合法性,返回校验结果
    @RequestMapping(value = {"/empName/{empName}","/empName/"},method = RequestMethod.GET)
    @ResponseBody
    public Msg testEmpName(@PathVariable(value = "empName",required = false) String empName){
        Msg msg = new Msg();

        //格式校验
        String empNameRegExp="(^[a-zA-Z\u2E80-\u9FFF0-9_-]{5,16}$)";
        if (empName==null || !empName.matches(empNameRegExp)) {
            msg.fail().setMsg("校验失败! 请你输入由汉字,英文字母和数字组成的5-16个字符!");
            return  msg;
        }

        //重复性校验
        boolean exist = employeeService.isEmpNameExist(empName);
        if (!exist){
            msg.success().setMsg("当前名称可以使用!");
        }else {
            msg.fail().setMsg("当前名称已存在!");
        }
        return msg;
    }

    //校验email合法性,返回校验结果
    @RequestMapping(value = {"/email/{email}","/email/"},method = RequestMethod.GET)
    @ResponseBody
    public Msg testEmail(@PathVariable(value = "email",required = false) String email){
        Msg msg = new Msg();

        //格式校验
        String emailRegExp="(^[a-z\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$)";
        if (email==null || !email.matches(emailRegExp)) {
            msg.fail().setMsg("校验失败! 请输入正确格式的邮箱!");
            return  msg;
        }

        //重复性校验
        boolean exist = employeeService.isEmailExist(email);

        if (!exist){
            msg.success().setMsg("当前邮箱可以使用!");
        }else {
            msg.fail().setMsg("当前邮箱已绑定账户!");
        }
        return msg;
    }

    //根据empId返回emp信息
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") String id){
        int empId = Integer.parseInt(id);
        Employee employee = employeeService.getEmpByEmpId(empId);
        Msg msg = new Msg();
        msg.success().add("emp", employee);
        return msg;
    }

    //根据empId从数据库中删除emp信息
    @RequestMapping(value = "/emp/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmp(@PathVariable("id") String id){
        int empId = Integer.parseInt(id);
        boolean result = employeeService.deleteEmpByEmpId(empId);
        Msg msg = new Msg();
        if (result){
            msg.success();
        }else {
            msg.fail();
        }
        return msg;
    }

    //根据empId从数据库中批量删除emp信息
    @RequestMapping(value = "/emps",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteEmps(@RequestParam("empId") String empIds_str){
        String[] empId_strs = empIds_str.split(",");
        ArrayList<Integer> empIds = new ArrayList<>(empId_strs.length);
        for (String empId_str : empId_strs) {
            empIds.add(Integer.parseInt(empId_str));
        }
        Msg msg = new Msg();
        for (Integer empId : empIds) {
            boolean b = employeeService.deleteEmpByEmpId(empId);
            //有一个失败则放弃全部修改
            if (!b){
                msg.fail();
                return msg;
            }
        }

        return msg.success();
    }

    //根据更新数据库中的emp信息
    @RequestMapping(value = "/emp",method = RequestMethod.PUT)
    @ResponseBody
    public Msg deleteEmp(@Valid Employee employee,BindingResult result){
        Msg msg = new Msg();
        if (result.hasErrors()) {
            //校验失败
            msg.fail();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                msg.add(fieldError.getField(), fieldError.getDefaultMessage());
            }
        }else {
            //校验成功
            if (employeeService.updateEmp(employee)){
                msg.success();
            }else {
                msg.fail().add("fail", "更新失败!");
            }
        }
        return msg;
    }
}
