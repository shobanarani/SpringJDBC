package com.shoba.SpringJDBC.SpringJDBC.service.impl;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.shoba.SpringJDBC.SpringJDBC.model.Employee;
import com.shoba.SpringJDBC.SpringJDBC.service.api.EmployeeService;
import com.shoba.SpringJDBC.SpringJDBC.service.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EmployeeServiceImpl implements EmployeeService
{

    @Autowired
    EmployeeDAO employeeDAO;

    public String getEmployeeNameById(int empId)
    {

        return employeeDAO.executeQueryForEmployeeName(empId);
    }

    public Employee getAllEmployeeDetailsById(int empId)
    {
        return employeeDAO.executeQueryForEmployeeDetails(empId);

    }

    public List<Employee> getAllEmployeeDetails()
    {
        return employeeDAO.executeQueryForAllEmployeeDetails();

    }


    public Employee getEmployeeNameByIdDept(int empId,String dept)
    {

        return employeeDAO.executeQueryForEmployeeNameByIdDept(empId,dept);
    }

    public Employee insertEmployee(Employee employee)
    {

        return employeeDAO.executeQueryInsertEmployee(employee);

    }


}
