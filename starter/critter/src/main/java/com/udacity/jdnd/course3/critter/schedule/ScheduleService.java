package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

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
}
