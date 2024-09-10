package ggudock.domain.subscription.repository;

import ggudock.domain.subscription.entity.SubscriptionSchedule;
import ggudock.domain.subscription.model.ScheduleState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public interface SubscriptionScheduleRepository extends JpaRepository<SubscriptionSchedule, Long> {
    List<SubscriptionSchedule> findAllBySubscription_Id(Long subscriptionId);
    void deleteAllBySubscription_Id(Long subscriptionId);
    boolean existsByAddress_IdAndDateAndScheduleState(Long addressId, LocalDate date, ScheduleState scheduleState);
    Page<SubscriptionSchedule> findAllByDateInAndSubscription_User_EmailAndScheduleState(List<LocalDate> date, String email, ScheduleState scheduleState, Pageable pageable);
    Page<SubscriptionSchedule> findAllByDayInAndSubscription_User_EmailAndScheduleState(List<DayOfWeek> day, String email, ScheduleState scheduleState, Pageable pageable);
    Page<SubscriptionSchedule> findAllByDateBetweenAndSubscription_User_EmailAndScheduleState(LocalDate start, LocalDate end, String email, ScheduleState scheduleState, Pageable pageable);
    Page<SubscriptionSchedule> findAllBySubscription_TitleAndSubscription_User_EmailAndScheduleState(String title, String email, ScheduleState scheduleState, Pageable pageable);
    Page<SubscriptionSchedule> findAllBySubscription_IdAndSubscription_User_EmailAndScheduleState(Long subscriptionId, String email, ScheduleState scheduleState, Pageable pageable);
    Page<SubscriptionSchedule> findAllBySubscription_User_EmailAndScheduleState(String email,ScheduleState state,Pageable pageable);
    Page<SubscriptionSchedule> findAllBySubscription_User_Email(String email,Pageable pageable);
}