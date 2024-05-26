package ggudock.domain.subscription.api;

import ggudock.config.oauth.utils.SecurityUtil;
import ggudock.domain.subscription.application.SubscriptionScheduleService;
import ggudock.domain.subscription.application.dto.SubscriptionScheduleResponse;
import ggudock.domain.subscription.model.ScheduleState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "구독 스케줄", description = "구독 스케줄 api")
@RequestMapping("/api/subscription-schedule")
public class SubscriptionScheduleController {

    private final SubscriptionScheduleService subscriptionScheduleService;

    @Operation(summary = "구독 스케줄 삭제", description = "구독Id를 받아서 관련된 구독 스케줄들을 삭제한다")
    @DeleteMapping("/{subscriptionDateId}")
    public ResponseEntity<?> deleteSubscriptionSchedule(@PathVariable("subscriptionDateId") Long subscriptionDateId) {
        subscriptionScheduleService.deleteSubscriptionDate(subscriptionDateId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "배달 완료", description = "구독Id를 받아서 관련된 구독 스케줄을 배달 완료 한다")
    @PatchMapping("/{subscriptionDateId}")
    public ResponseEntity<?> changeSubscriptionSchedule(@PathVariable("subscriptionDateId") Long subscriptionDateId) {
        subscriptionScheduleService.offSubscriptionSchedule(subscriptionDateId);
        return new ResponseEntity<>(null, HttpStatusCode.valueOf(204));
    }

    @Operation(summary = "구독 스케줄 받기", description = "구독 스케줄 Id를 받아서 확인한다.")
    @GetMapping
    public ResponseEntity<SubscriptionScheduleResponse> getDetail(@RequestParam("subscriptionDateId") Long subscriptionDateId) {
        return new ResponseEntity<>(subscriptionScheduleService.getDetail(subscriptionDateId), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "모든 구독 스케줄 받기", description = "모든 구독 스케줄을 Page 로 받는다.")
    @GetMapping("/all")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionDatePage(@RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionSchedulePage(SecurityUtil.getCurrentName(), page), HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "요일로 구독 스케줄 받기", description = "요일을 선택해서 구독 스케줄을 Page 로 받는다.")
    @GetMapping("/search-day")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionDatePageByDay(@RequestParam("day") List<DayOfWeek> day,
                                                                                           @RequestParam("state") ScheduleState state,
                                                                                           @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionSchedulePageByDayList(day, SecurityUtil.getCurrentName(), state, page)
                , HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "날짜로 구독 스케줄 받기", description = "날짜를 선택해서 구독 스케줄을 Page 로 받는다.")
    @GetMapping("/search-date")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionDatePageByDate(@RequestParam("date") List<LocalDate> date,
                                                                                            @RequestParam("state") ScheduleState state,
                                                                                            @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionSchedulePageByDates(date, SecurityUtil.getCurrentName(), state, page),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "날짜 2개를 정해서 그 사이에 있는 구독 스케줄 받기", description = "날짜 두개를 선택해서 구독 스케줄을 Page 로 받는다.")
    @GetMapping("/search-between")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionDatePageByDates(@RequestParam("start") LocalDate start,
                                                                                             @RequestParam("end") LocalDate end,
                                                                                             @RequestParam("state") ScheduleState state,
                                                                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionSchedulePageByDates(start, end, SecurityUtil.getCurrentName(), state, page),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "구독 제목으로 구독 스케줄 받기", description = "구독제목으로 구독 스케줄을 Page 로 받는다.")
    @GetMapping("/search-title")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionDatePageByTitle(@RequestParam("title") String title,
                                                                                             @RequestParam("state") ScheduleState state,
                                                                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionSchedulePageByTitle(title, SecurityUtil.getCurrentName(), state, page),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "구독으로 구독 스케줄 받기", description = "구독으로  구독 스케줄 Page 로 받는다.")
    @GetMapping("/search-subscription")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionScheduleBySubscription(@RequestParam("subscriptionId") Long subscriptionId,
                                                                                                    @RequestParam("state") ScheduleState state,
                                                                                                    @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionScheduleBySubscription(subscriptionId, SecurityUtil.getCurrentName(), state, page),
                HttpStatusCode.valueOf(200));
    }

    @Operation(summary = "구독상태로 구독 스케줄 받기", description = "구독 상태로  구독 스케줄 Page 로 받는다.")
    @GetMapping("/search-state")
    public ResponseEntity<Page<SubscriptionScheduleResponse>> getSubscriptionScheduleByState(@RequestParam("state") ScheduleState state,
                                                                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(subscriptionScheduleService.getSubscriptionScheduleByState(SecurityUtil.getCurrentName(), state, page),
                HttpStatusCode.valueOf(200));
    }
}