package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    PetRepository petRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    public Schedule save(Schedule schedule) {
        scheduleRepository.save(schedule);
        assert(schedule.getId() != null);

        schedule.getEmployees().forEach(employee -> employee.addToSchedule(schedule));
        schedule.getPets().forEach(pet -> pet.addToSchedule(schedule));

        return schedule;
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getByPet(Pet pet) {
        return pet.getSchedules();
    }

    public List<Schedule> getByEmployee(Employee employee) {
        return employee.getSchedules();
    }

    public List<Schedule> getByCustomer(Customer customer) {
        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();

        pets.forEach(pet -> schedules.addAll(pet.getSchedules()));

        return schedules;
    }
}
