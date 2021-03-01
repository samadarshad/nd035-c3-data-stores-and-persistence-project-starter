package com.udacity.jdnd.course3.critter.user;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends UserRepository<Employee> {

}