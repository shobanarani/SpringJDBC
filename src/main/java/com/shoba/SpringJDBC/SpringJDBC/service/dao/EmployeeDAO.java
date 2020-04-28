package com.shoba.SpringJDBC.SpringJDBC.service.dao;

import com.shoba.SpringJDBC.SpringJDBC.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EmployeeDAO {

    @Resource
    private DataSource employeeDataSource;

    private SimpleJdbcCall procedure;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    Employee employee;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PostConstruct
    private void init()
     {

         this.procedure = new SimpleJdbcCall(employeeDataSource).withSchemaName("shoba_db").
                          withProcedureName("getSalaryUser");
         this.jdbcTemplate = new JdbcTemplate(employeeDataSource);
         this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(employeeDataSource);

     }

     public String executeProcedure(int id)
     {

         SqlParameterSource in = new MapSqlParameterSource().addValue("empId",id);
         Map<String,Object> out = procedure.execute(in);
         Employee employee = new Employee();
         String sal = out.get("out_salary").toString();
         return sal;
     }

    public String executeQueryForEmployeeName(int empId)
    {
         System.out.println("empId "+empId);
         String sql = "select empName from employee where empId=?";
         String name = jdbcTemplate.queryForObject(sql,new Object[]{empId},String.class);
         return name;
    }

    public Employee executeQueryForEmployeeDetails(int empId)
    {
        System.out.println("empId "+empId);
        String  sql = "select * from employee where empId=?";
        Employee employeeDetails =
                (Employee) jdbcTemplate.queryForObject(sql,new Object[]{empId},new BeanPropertyRowMapper(Employee.class));
        System.out.println("employeeDetaiks"+employeeDetails);
        return employeeDetails;
    }

    public List<Employee> executeQueryForAllEmployeeDetails()
    {
        String  sql = "select * from employee ";
        List<Employee> employeeDetails =
                jdbcTemplate.query(sql,new BeanPropertyRowMapper(Employee.class));
        System.out.println("employeeDetails"+employeeDetails);
        return employeeDetails;
    }



    public Employee executeQueryForEmployeeNameByIdDept(int empID,String empDept)
    {
        System.out.println("empId "+empID);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("empDept",empDept);
        mapSqlParameterSource.addValue("empID",empID);
        String sql = "select * from employee where empID =:empID and empDept =:empDept";
        Employee employeedetails = namedParameterJdbcTemplate.queryForObject(sql,mapSqlParameterSource,BeanPropertyRowMapper.newInstance(Employee.class));
        return employeedetails;
    }

    public Employee executeQueryInsertEmployee(Employee employee)

    {
        int empID = employee.getEmpID();

        String empName = employee.getEmpName();
        String empDept = employee.getEmpDept();
        jdbcTemplate.update("INSERT INTO EMPLOYEE VALUES (?, ?, ?)", empID, empName, empDept);
        return employee;
    }
}

