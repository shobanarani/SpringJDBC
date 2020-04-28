package com.shoba.SpringJDBC.SpringJDBC.rs.v1;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.shoba.SpringJDBC.SpringJDBC.model.Employee;
import com.shoba.SpringJDBC.SpringJDBC.service.dao.EmployeeDAO;
import com.shoba.SpringJDBC.SpringJDBC.service.impl.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.support.NullValue;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.List;

//import javax.inject.Inject;
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    EmployeeServiceImpl employeeServiceImpl;

    @Autowired
    Employee employee;

    @GetMapping("/getSalary/{id}")
    public String getSalary(@PathVariable int id)
    {
        String empName = employeeServiceImpl.getEmployeeNameById(id);
        return empName;
    }

    @GetMapping("/employeeDetails/{id}")
    public Employee getAllEmployeeDetails(@PathVariable int id)
    {
        employee = employeeServiceImpl.getAllEmployeeDetailsById(id);
        return employee;
    }

    @GetMapping("/employeeDetails/all")
    public List<Employee> getAllEmployeeDetails()
    {
        List<Employee> employee = employeeServiceImpl.getAllEmployeeDetails();
        return employee;
    }

    //Query Paramters
    //http://localhost:8080/employee/getSalary/?id=3

    @GetMapping("/getSalary")
    public String getSalaryQuery(@RequestParam int id)
    {
        String empName = employeeServiceImpl.getEmployeeNameById(id);
        return empName;
    }

    //Query Paramters
   // http://localhost:8080/employee/getEmployeeName/?id=2&dept=IT

    @GetMapping("/getEmployeeName")
    public Employee getEmployeeNameTwoQueryParams(@RequestParam int id, @RequestParam String dept  )
    {
        System.out.println("id"+id+"dept"+dept);
        Employee empName = employeeServiceImpl.getEmployeeNameByIdDept(id,dept);
        return empName;
    }

    @PostMapping("/createEmployee")
    public ResponseEntity<String> createEmployee(@RequestBody Employee employee, @RequestHeader HttpHeaders httpHeaders)
    {
        System.out.println("employeename"+employee.getEmpName());

        if(employee.getEmpID() < 0  || employee.getEmpName().isEmpty()   || employee.getEmpDept() == null)

        {
            return new ResponseEntity<String>("Some parameter are missing",HttpStatus.BAD_REQUEST);
        }
          else
        {
            employeeServiceImpl.insertEmployee(employee);
            return new ResponseEntity<String>("Good",HttpStatus.OK);

       }

    }

}
