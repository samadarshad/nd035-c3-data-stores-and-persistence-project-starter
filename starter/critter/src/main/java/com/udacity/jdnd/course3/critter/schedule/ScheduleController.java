package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.UserController;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import com.udacity.jdnd.course3.critter.user.employee.Employee;
import com.udacity.jdnd.course3.critter.user.employee.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;

    @Autowired
    PetService petService;

    @Autowired
    EmployeeService employeeService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = convertDTOToEntity(scheduleDTO);
        schedule = scheduleService.save(schedule);

        if (scheduleDTO.getEmployeeIds() != null) {
            Schedule finalSchedule = schedule;
            scheduleDTO.getEmployeeIds().forEach(employeeId -> employeeService.get(employeeId).addToSchedule(finalSchedule));
        }

        if (scheduleDTO.getPetIds() != null) {
            Schedule finalSchedule = schedule;
            scheduleDTO.getPetIds().forEach(petId -> petService.get(petId).addToSchedule(finalSchedule));
        }
        return convertEntityToDTO(schedule);
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleService.list();
        return schedules.stream().map(ScheduleController::convertEntityToDTO).collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }

    private static ScheduleDTO convertEntityToDTO(Schedule entity) {
        ScheduleDTO dto = new ScheduleDTO();
        BeanUtils.copyProperties(entity, dto);
        if (entity.getPets() != null) {
            dto.setPetIds(
                    entity.getPets().stream().map(Pet::getId).collect(Collectors.toList())
            );
        }
        if (entity.getEmployees() != null) {
            dto.setEmployeeIds(
                    entity.getEmployees().stream().map(Employee::getId).collect(Collectors.toList())
            );
        }
        return dto;
    }

    private Schedule convertDTOToEntity(ScheduleDTO dto) {
        Schedule entity = new Schedule();
        BeanUtils.copyProperties(dto, entity);
        if (dto.getPetIds() != null) {
            entity.setPets(
                    dto.getPetIds().stream().map(petId -> petService.get(petId)).collect(Collectors.toList())
            );
        }
        if (dto.getEmployeeIds() != null) {
            entity.setEmployees(
                    dto.getEmployeeIds().stream().map(employeeId -> employeeService.get(employeeId)).collect(Collectors.toList())
            );
        }
        return entity;
    }
}
