package com.shoba.SpringJDBC.SpringJDBC.service.api;

import com.shoba.SpringJDBC.SpringJDBC.model.Employee;

public interface EmployeeService {

    public String getEmployeeNameById(int id);

    public Employee getAllEmployeeDetailsById(int id);

    public Employee insertEmployee(Employee employee);

}
