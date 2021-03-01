package com.udacity.jdnd.course3.critter.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;


public interface ScheduleConsumer {
//    @ManyToMany
//    List<Schedule> schedules = new ArrayList<>();

    Boolean addToSchedule(Schedule schedule);
}
