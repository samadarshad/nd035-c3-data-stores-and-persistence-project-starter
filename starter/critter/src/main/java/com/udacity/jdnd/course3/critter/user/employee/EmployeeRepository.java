package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.UserRepository;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

@Repository
public interface EmployeeRepository extends UserRepository<Employee> {
    List<Employee> findAllBySkillsInAndDaysAvailable(Set<EmployeeSkill> skills, DayOfWeek day);
}