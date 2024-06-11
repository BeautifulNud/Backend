package ggudock.domain.order.application;

import ggudock.domain.item.entity.Item;
import ggudock.domain.item.repository.ItemRepository;
import ggudock.domain.order.api.dto.OrderDayRequest;
import ggudock.domain.order.api.dto.OrderPeriodRequest;
import ggudock.domain.order.application.dto.OrderResponse;
import ggudock.domain.order.entity.CustomerOrder;
import ggudock.domain.order.repository.OrderRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
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
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    public void saveOrderByPeriod(OrderPeriodRequest orderPeriodRequest,String email, Long itemId){
        User user = getUser(email);
        Item item = getItem(itemId);
        int totalPrice = getTotalPriceByPeriod(orderPeriodRequest, item);
        CustomerOrder customerOrder = createOrder(user, item, totalPrice);
        orderRepository.save(customerOrder);
    }

    public void saveOrderByDay(OrderDayRequest orderDayRequest,String email,Long itemId) {
        User user = getUser(email);
        Item item = getItem(itemId);
        int totalPrice = getTotalPriceByDay(orderDayRequest, item);
        CustomerOrder customerOrder = createOrder(user, item, totalPrice);
        orderRepository.save(customerOrder);
    }

    public void deleteOrder(Long orderId){
        delete(orderId);
    }

    public OrderResponse getDetail(Long orderId){
        return createOrderResponse(orderId);
    }

    public Page<OrderResponse> getOrderPage(String email,int page){
        PageRequest pageRequest = createPageRequest(page);
        Page<CustomerOrder> customerOrderPage = createOrderPage(email, pageRequest);
        return createOrderResponsePage(customerOrderPage);
    }

    private void delete(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    private OrderResponse createOrderResponse(Long orderId) {
        CustomerOrder order = getOrder(orderId);
        return OrderResponse.builder()
                .totalPrice(order.getTotalPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }

    private CustomerOrder getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
    }

    private int getCountByPeriod(LocalDate startDate,LocalDate endDate,List<DayOfWeek> days) {
        int cnt = 0;
        while (!startDate.isAfter(endDate)) {
            for (DayOfWeek day : days) {
                if (day.equals(DayOfWeek.of(startDate.getDayOfWeek().getValue()))) {
                    cnt++;
                }
            }
            startDate = startDate.plusDays(1);
        }

        return cnt;
    }

    private int getTotalPriceByPeriod(OrderPeriodRequest orderPeriodRequest, Item item) {
        return item.getPrice() * getCountByPeriod(orderPeriodRequest.getStartDate(),
                orderPeriodRequest.getEndDate(), orderPeriodRequest.getDays());
    }

    private static int getTotalPriceByDay(OrderDayRequest orderDayRequest, Item item) {
        return item.getPrice() * orderDayRequest.getDates().size();
    }

    private static CustomerOrder createOrder(User user, Item item, int totalPrice) {
        return CustomerOrder.builder()
                .user(user)
                .item(item)
                .totalPrice(totalPrice)
                .build();
    }

    private Page<CustomerOrder> createOrderPage(String email, PageRequest pageRequest) {
        return orderRepository.findAllByUser_Email(email, pageRequest);
    }

    private static PageRequest createPageRequest(int page) {
        return PageRequest.of(page, 10);
    }

    private Page<OrderResponse> createOrderResponsePage(Page<CustomerOrder> customerOrderPage){
        return customerOrderPage.map(OrderResponse::new);
    }

    private Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
    }

    private User getUser(String email) {
        return userRepository.findByEmail(email);
    }
}