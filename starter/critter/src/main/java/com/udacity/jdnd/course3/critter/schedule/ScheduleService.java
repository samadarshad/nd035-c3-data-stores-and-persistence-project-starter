package com.udacity.jdnd.course3.critter.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    ScheduleRepository scheduleRepository;

    public Schedule save(Schedule schedule) {
        // save to pet
        // save to employee
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> list() {
        return scheduleRepository.findAll();
    }
}
