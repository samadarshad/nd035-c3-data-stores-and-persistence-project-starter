package com.udacity.jdnd.course3.critter.user.employee;

import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import com.udacity.jdnd.course3.critter.user.UserNotFoundException;
import com.udacity.jdnd.course3.critter.user.UserService;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.customer.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService extends UserService<Employee> {
    @Autowired
    EmployeeRepository employeeRepository;

    public Employee get(Long id) {
        Optional<Employee> optional = employeeRepository.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new UserNotFoundException();
        }
    }

    public List<Employee> getByName(String name) {
        return employeeRepository.getAllByName(name);
    }

    public List<Employee> list() {
        return employeeRepository.findAll();
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void setAvailability(Set<DayOfWeek> days, Employee employee) {
        employee.setDaysAvailable(days);
    }

    public List<Employee> findEmployeesForService(Set<EmployeeSkill> skills, DayOfWeek day) {
        List<Employee> possibleEmployees = employeeRepository.findDistinctBySkillsInAndDaysAvailable(skills, day);
        return possibleEmployees.stream().filter(employee -> employee.getSkills().containsAll(skills)).collect(Collectors.toList());
    }
}
