package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.schedule.ScheduleConsumer;
import com.udacity.jdnd.course3.critter.user.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Pet implements ScheduleConsumer {
    @Id
    @GeneratedValue
    private Long id;
    private PetType type;
    @Nationalized
    private String name;
    private LocalDate birthDate;
    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer owner;

//    @ManyToMany
//    private List<Schedule> schedules = new ArrayList<>();

    public Boolean addToSchedule(Schedule schedule) {
        return schedules.add(schedule);
    }
}
