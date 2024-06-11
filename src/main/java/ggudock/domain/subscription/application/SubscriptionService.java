package ggudock.domain.subscription.application;

import ggudock.domain.address.entity.Address;
import ggudock.domain.address.repository.AddressRepository;
import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.subscription.api.dto.SubscriptionDayRequest;
import ggudock.domain.subscription.api.dto.SubscriptionPeriodRequest;
import ggudock.domain.subscription.application.component.ExistSubscription;
import ggudock.domain.subscription.application.dto.SubscriptionResponse;
import ggudock.domain.subscription.entity.Subscription;
import ggudock.domain.subscription.entity.SubscriptionSchedule;
import ggudock.domain.subscription.model.ScheduleState;
import ggudock.domain.subscription.model.State;
import ggudock.domain.subscription.model.SubType;
import ggudock.domain.subscription.repository.SubscriptionScheduleRepository;
import ggudock.domain.subscription.repository.SubscriptionRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionScheduleRepository subscriptionScheduleRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ExistSubscription existSubscription;

    public boolean saveSubscriptionByPeriod(SubscriptionPeriodRequest subscriptionRequest, String email, Long addressId, Long itemId){
        User user = getUser(email);
        Address address = getAddress(addressId);
        Item item = getItem(itemId);
        Subscription saveSubscription = saveSubscriptionByPeriod(subscriptionRequest, user, item);
        existSubscription.setExistSubscription(false);
        createPeriodSubscriptionDates(subscriptionRequest, saveSubscription, address);
        createPrice(saveSubscription);
        return existSubscription.isExistSubscription();
    }

    public boolean saveSubscriptionByDay(SubscriptionDayRequest subscriptionRequest, String email, Long addressId, Long itemId){
        User user = getUser(email);
        Address address = getAddress(addressId);
        Item item = getItem(itemId);
        Subscription saveSubscription = saveSubscriptionByDay(subscriptionRequest, user, item);
        existSubscription.setExistSubscription(false);
        List<LocalDate> dates = subscriptionRequest.getDates();
        checkExistSubscription(addressId, dates);
        createDaySubscriptionDates(saveSubscription, dates, address);
        createPrice(saveSubscription);
        return existSubscription.isExistSubscription();
    }

    private void createPrice(Subscription saveSubscription) {
        Integer price = subscriptionRepository.createPrice(saveSubscription.getId());
        saveSubscription.createPrice(price);
    }

    private void checkExistSubscription(Long addressId, List<LocalDate> dates) {
        for (LocalDate date : dates) {
            if (existDaySubscription(addressId, date)) {
                changeState();
                break;
            }
        }
    }

    private Subscription saveSubscriptionByDay(SubscriptionDayRequest subscriptionRequest, User user, Item item) {
        Subscription subscription = createSubscriptionByDay(subscriptionRequest, user, item);
        return save(subscription);
    }

    private Subscription saveSubscriptionByPeriod(SubscriptionPeriodRequest subscriptionRequest, User user, Item item) {
        Subscription subscription = createSubscriptionByPeriod(subscriptionRequest, user, item);
        return save(subscription);
    }

    private void changeState() {
        existSubscription.setExistSubscription(true);
    }
    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
    }

    private boolean existDaySubscription(Long addressId, LocalDate date) {
        return subscriptionScheduleRepository.existsByAddress_IdAndDateAndScheduleState(addressId, date, ScheduleState.ON);
    }


    private Address getAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ADDRESS));
    }

    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public void saveSubscriptionSchedule(SubscriptionSchedule subscriptionSchedule) {
        subscriptionScheduleRepository.save(subscriptionSchedule);
    }

    public void deleteSubscription(Long subscriptionId) {
        deleteAllSubscriptionDate(subscriptionId);
        delete(getSubscription(subscriptionId));
    }

    private void deleteAllSubscriptionDate(Long subscriptionId) {
        subscriptionScheduleRepository.deleteAllBySubscription_Id(subscriptionId);
    }

    public void delete(Subscription subscription) {
        subscriptionRepository.delete(subscription);
    }

    @Transactional(readOnly = true)
    public Subscription getSubscription(Long subscriptionId) {
        return subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_Subscription));
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionResponse> getSubscriptionPage(String email,int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Subscription> subscriptionPage = createSubscriptionPage(email,pageRequest);
        return createSubscriptionResponsePage(subscriptionPage);
    }

    @Transactional(readOnly = true)
    public SubscriptionResponse getDetail(Long subscriptionId) {
        return createSubscriptionResponse(subscriptionId);
    }

    public SubscriptionResponse terminateSubscription(Long subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        if (subscription.getSubType() == SubType.PERIOD) {
            changeOffPeriodSubscription(subscription);
        } else {
            changeOffDaySubscription(subscriptionId, subscription);
        }

        return getDetail(subscriptionId);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionResponse> getSubscriptionPageByOn(int page, String email) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Subscription> subscriptionPage = createSubscriptionPageByOn(pageRequest, email);
        return createSubscriptionResponsePage(subscriptionPage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionResponse> getSubscriptionPageByOff(int page, String email) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Subscription> subscriptionPage = createSubscriptionPageByOff(pageRequest, email);
        return createSubscriptionResponsePage(subscriptionPage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionResponse> getSubscriptionPageByPeriod(int page, String email) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Subscription> subscriptionPage = createSubscriptionPageByPeriod(pageRequest, email);
        return createSubscriptionResponsePage(subscriptionPage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionResponse> getSubscriptionPageByDay(int page, String email) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Subscription> subscriptionPage = createSubscriptionPageByDay(pageRequest, email);
        return createSubscriptionResponsePage(subscriptionPage);
    }

    @Transactional(readOnly = true)
    public Page<SubscriptionResponse> getSubscriptionPageByTitle(String title, String email, int page) {
        PageRequest pageRequest = createPageRequest(page);
        Page<Subscription> subscriptionPage = createSubscriptionPageByTitle(title, pageRequest, email);
        return createSubscriptionResponsePage(subscriptionPage);
    }

    private static Subscription createSubscriptionByDay(SubscriptionDayRequest subscriptionRequest, User user,Item item) {
        return Subscription.builder()
                .state(State.ON)
                .subType(SubType.DAY)
                .startDate(subscriptionRequest.getStartDate())
                .endDate(subscriptionRequest.getEndDate())
                .title(subscriptionRequest.getTitle())
                .user(user)
                .item(item)
                .build();
    }

    private static Subscription createSubscriptionByPeriod(SubscriptionPeriodRequest subscriptionRequest, User user,Item item) {
        return Subscription.builder()
                .state(State.ON)
                .subType(SubType.PERIOD)
                .startDate(subscriptionRequest.getStartDate())
                .endDate(subscriptionRequest.getEndDate())
                .title(subscriptionRequest.getTitle())
                .user(user)
                .item(item)
                .build();
    }


    private SubscriptionResponse createSubscriptionResponse(Long subscriptionId) {
        Subscription subscription = getSubscription(subscriptionId);
        return SubscriptionResponse.builder()
                .state(subscription.getState())
                .subType(subscription.getSubType())
                .periodDays(subscription.getPeriodDays())
                .startDate(subscription.getStartDate())
                .endDate(subscription.getEndDate())
                .title(subscription.getTitle())
                .price(subscription.getPrice())
                .build();
    }

    // 구독 전체 페이징
    private Page<Subscription> createSubscriptionPage(String email,PageRequest pageRequest) {
        return subscriptionRepository.findAllByUser_Email(email,pageRequest);
    }

    /**
     구독 생성할때 구독 날짜 저장하는 메서드들
     */

    private void createDaySubscriptionDates(Subscription subscription, List<LocalDate> dates, Address address) {
        for (LocalDate date : dates) {
            SubscriptionSchedule subscriptionSchedule = createSubscriptionDate(subscription, date, address);
            saveSubscriptionSchedule(subscriptionSchedule);
        }
    }

    private static SubscriptionSchedule createSubscriptionDate(Subscription subscription, LocalDate date, Address address) {
        return SubscriptionSchedule.builder()
                .date(date)
                .subscription(subscription)
                .address(address)
                .build();
    }

    private void createPeriodSubscriptionDates(SubscriptionPeriodRequest subscriptionRequest, Subscription subscription, Address address) {
        List<DayOfWeek> days = subscriptionRequest.getDays();
        LocalDate date = subscription.getStartDate();
        createPeriodDates(subscription, days, date, address);
    }

    private void createPeriodDates(Subscription subscription, List<DayOfWeek> days, LocalDate date, Address address) {
        while (!date.isAfter(subscription.getEndDate())) {
            for (DayOfWeek day : days) {
                if (day.equals(DayOfWeek.of(date.getDayOfWeek().getValue()))) {
                    if (existDaySubscription(address.getId(), date))
                        changeState();
                    SubscriptionSchedule subscriptionSchedule = createSubscriptionDate(subscription, date, address);
                    saveSubscriptionSchedule(subscriptionSchedule);
                }
            }
            date = date.plusDays(1);
        }
    }

    /**
     *  구독 상태 바꾸는 메서드들
     */
    private void changeOffDaySubscription(Long subscriptionId, Subscription subscription) {
        List<SubscriptionSchedule> subscriptionScheduleList = getSubscriptionDateList(subscriptionId);
        LocalDate date = getEndDate(subscriptionScheduleList);
        if (date.isBefore(LocalDate.now())) {
            subscription.offSubscription();
        }
    }

    private LocalDate getEndDate(List<SubscriptionSchedule> subscriptionScheduleList) {
        int size = subscriptionScheduleList.size();
        return subscriptionScheduleList.get(size - 1).getDate();
    }

    private List<SubscriptionSchedule> getSubscriptionDateList(Long subscriptionId) {
        return subscriptionScheduleRepository.findAllBySubscription_Id(subscriptionId);
    }

    private void changeOffPeriodSubscription(Subscription subscription) {
        if (subscription.getEndDate().isBefore(LocalDate.now())) {
            subscription.offSubscription();
        }
    }

    /**
     *  구독 페이징 처리하는 메서드들
     */

    private static Page<SubscriptionResponse> createSubscriptionResponsePage(Page<Subscription> subscriptionPage) {
        return subscriptionPage.map(SubscriptionResponse::new);
    }

    private static PageRequest createPageRequest(int page) {
        return PageRequest.of(page, 10);
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Page<Subscription> createSubscriptionPageByOn(PageRequest pageRequest, String email) {
        return subscriptionRepository.findAllByStateAndUser_Email(State.ON, email, pageRequest);
    }

    private Page<Subscription> createSubscriptionPageByOff(PageRequest pageRequest, String email) {
        return subscriptionRepository.findAllByStateAndUser_Email(State.OFF, email, pageRequest);
    }

    private Page<Subscription> createSubscriptionPageByPeriod(PageRequest pageRequest, String email) {
        return subscriptionRepository.findAllBySubTypeAndUser_Email(SubType.PERIOD, email, pageRequest);
    }

    private Page<Subscription> createSubscriptionPageByDay(PageRequest pageRequest, String email) {
        return subscriptionRepository.findAllBySubTypeAndUser_Email(SubType.DAY, email, pageRequest);
    }

    private Page<Subscription> createSubscriptionPageByTitle(String title, PageRequest pageRequest, String email) {
        return subscriptionRepository.findAllByTitleAndUser_Email(title, email, pageRequest);
    }
}
