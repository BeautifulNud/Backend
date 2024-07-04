package ggudock.domain.subscription.application;

import ggudock.domain.subscription.application.dto.SubscriptionScheduleResponse;
import ggudock.domain.subscription.entity.SubscriptionSchedule;
import ggudock.domain.subscription.model.ScheduleState;
import ggudock.domain.subscription.repository.SubscriptionScheduleRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SubscriptionScheduleService {

    private final SubscriptionScheduleRepository subscriptionScheduleRepository;

    public void deleteSubscriptionDate(Long subscriptionId) {
        delete(getSubscriptionSchedule(subscriptionId));
    }

    public void delete(SubscriptionSchedule subscriptionSchedule) {
        subscriptionScheduleRepository.delete(subscriptionSchedule);
    }

    public void offSubscriptionSchedule(Long subscriptionScheduleId) {
        SubscriptionSchedule subscriptionSchedule = getSubscriptionSchedule(subscriptionScheduleId);
        ChangeState(subscriptionSchedule);
    }

    private static void ChangeState(SubscriptionSchedule subscriptionSchedule) {
        subscriptionSchedule.changeState();
    }


    @Transactional(readOnly = true)
    public SubscriptionSchedule getSubscriptionSchedule(Long subscriptionDateId) {
        return subscriptionScheduleRepository.findById(subscriptionDateId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_Subscription_DATE));
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionSchedulePage(String email,int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePage(email,pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    @Transactional(readOnly = true)
    public SubscriptionScheduleResponse getDetail(Long subscriptionDateId) {
        return createResponse(subscriptionDateId);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionSchedulePageByDayList(List<DayOfWeek> dayList, String email, ScheduleState state, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePageByDay(dayList, email, state, pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionSchedulePageByDates(List<LocalDate> dates, String email, ScheduleState state, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePageByDateIn(dates, email, state, pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionSchedulePageByDates(LocalDate start, LocalDate end, String email, ScheduleState state, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePageByDates(start, end, email, state, pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionSchedulePageByTitle(String title, String email, ScheduleState state, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePageByTitle(title, email, state, pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionScheduleBySubscription(Long subscriptionId, String email, ScheduleState state, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePageBySubscription(subscriptionId, email, state, pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionScheduleResponse> getSubscriptionScheduleByState(String email, ScheduleState state, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<SubscriptionSchedule> subscriptionSchedulePage = createSubscriptionSchedulePageByState(email, state, pageRequest);
        return createSubscriptionScheduleResponsePage(subscriptionSchedulePage);
    }

    private SubscriptionScheduleResponse createResponse(Long subscriptionDateId) {
        SubscriptionSchedule subscriptionSchedule = getSubscriptionSchedule(subscriptionDateId);
        return new SubscriptionScheduleResponse(subscriptionSchedule);
    }

    /**
     * 페이징 처리하는 메소드
     */

    private static Page<SubscriptionScheduleResponse> createSubscriptionScheduleResponsePage(Page<SubscriptionSchedule> subscriptionDatePage) {
        return subscriptionDatePage.map(SubscriptionScheduleResponse::new);
    }

    private static PageRequest createPageRequest(int page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "date");
        return PageRequest.of(page, 10, sort);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePage(String email,PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllBySubscription_User_Email(email,pageRequest);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePageByDay(List<DayOfWeek> days, String email, ScheduleState state, PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllByDayInAndSubscription_User_EmailAndScheduleState(days, email, state, pageRequest);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePageByDateIn(List<LocalDate> dates, String email, ScheduleState state, PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllByDateInAndSubscription_User_EmailAndScheduleState(dates, email, state, pageRequest);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePageByDates(LocalDate start, LocalDate end, String email, ScheduleState state, PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllByDateBetweenAndSubscription_User_EmailAndScheduleState(start, end, email, state, pageRequest);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePageByTitle(String name, String email, ScheduleState state, PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllBySubscription_Order_Item_NameAndSubscription_User_EmailAndScheduleState(name, email, state, pageRequest);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePageBySubscription(Long subscriptionId, String email, ScheduleState state, PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllBySubscription_IdAndSubscription_User_EmailAndScheduleState(subscriptionId, email, state, pageRequest);
    }

    private Page<SubscriptionSchedule> createSubscriptionSchedulePageByState(String email, ScheduleState state, PageRequest pageRequest) {
        return subscriptionScheduleRepository.findAllBySubscription_User_EmailAndScheduleState(email, state, pageRequest);
    }
}