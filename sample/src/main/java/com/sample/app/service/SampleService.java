package com.sample.app.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sample.app.model.Order;
import com.sample.app.model.QOrder;
import com.sample.app.model.User;
import com.sample.app.repository.OrderRepository;
import com.sample.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SampleService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final JPAQueryFactory jpaQueryFactory;

    public void createUser() {
        this.userRepository.save(User.builder()
                .name(String.join("-", "name",
                        String.valueOf(Instant.now().toEpochMilli()))).build());
    }

    @Transactional
    public void doOrder(final Long id) {
        final User user = this.userRepository.getOne(id);
        user.doOrders(Order.builder().status("ORDER").build());
        this.userRepository.save(user);
    }

    public Page<Order> selectOrder(final Map<String, String> maps, final Pageable pageable) {
        return this.orderRepository.findAllByDateBetweenOrderByDateDesc(pageable,
                LocalDateTime.of(this.stringToLocalDate(maps.get("start")), LocalTime.of(0, 0, 0)),
                LocalDateTime.of(this.stringToLocalDate(maps.get("end")),   LocalTime.of(23,59,59)));
    }

    public Map<String, Page<Order>> selects(final Predicate predicate, final Pageable pageable) {
        final Map<String, Page<Order>> maps = new LinkedHashMap<>();
        maps.put("normal",   this.orderRepository.findAll(predicate, pageable));
        maps.put("JPQL",     this.orderRepository.findAllByQueryOrderByIdDesc(pageable));
        final QOrder order = QOrder.order;
        final List<Order> queryDSL = this.jpaQueryFactory.from(order).select(order)
                .orderBy(order.id.desc()).offset(pageable.getOffset())
                .limit(pageable.getPageSize()).fetch();
        maps.put("QueryDSL", new PageImpl<>(queryDSL, pageable, queryDSL.size()));
        log.debug("normal  : {}", maps.get("normal"));
        log.debug("JPQL    : {}", maps.get("JPQL"));
        log.debug("QueryDSL: {}", maps.get("QueryDSL"));
        return maps;
    }

    public Map<String, Order> select(final Long id) {
        final Map<String, Order> maps = new LinkedHashMap<>();
        maps.put("normal",  this.orderRepository.findById(id).orElseGet(Order::new));
        maps.put("JPQL",    this.orderRepository.findByIdByQuery(id).orElseGet(Order::new));
        final QOrder order = QOrder.order;
        final Order queryDSL = this.jpaQueryFactory.from(order).select(order)
                .where(order.id.eq(id)).fetchOne();
        maps.put("QueryDSL", queryDSL);
        log.debug("normal  : {}", maps.get("normal"));
        log.debug("JPQL    : {}", maps.get("JPQL"));
        log.debug("QueryDSL: {}", maps.get("QueryDSL"));
        return maps;
    }

    private LocalDate stringToLocalDate(final String date) {
        return Optional.ofNullable(date)
                .map(s -> LocalDate.parse(s, DateTimeFormatter.ISO_DATE))
                .orElseGet(LocalDate::now);
    }
}
