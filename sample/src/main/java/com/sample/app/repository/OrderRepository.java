package com.sample.app.repository;

import com.sample.app.model.Order;
import com.sample.app.model.QOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>,
        QuerydslPredicateExecutor<Order>, QuerydslBinderCustomizer<QOrder> {

    // 바인딩 사용자 정의
    @Override
    default void customize(QuerydslBindings bindings, QOrder root) {
        // 모든 String 속성에 대해 대/소문자 를 무시
        //bindings.bind(String.class)
                //.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
        // id 로 조회 제외: ?id=x 조회시 필터링 안됨
        //bindings.excluding(root.id);
        // orderId 로 조회 제외: ?status=xxx... 조회시 필터링 안됨
        //bindings.excluding(root.status);
    }

    //@EntityGraph(value = "graph.OrderWithOrderItem", type = EntityGraph.EntityGraphType.LOAD)
    Page<Order> findAllByDateBetweenOrderByDateDesc(Pageable pageable, LocalDateTime start, LocalDateTime end);

    @Query("SELECT o FROM Order o ORDER BY o.id DESC")
    //@Query(value= "SELECT o.* FROM sample.order o ORDER BY o.id DESC", nativeQuery = true)
    Page<Order> findAllByQueryOrderByIdDesc(Pageable pageable);

    Optional<Order> findById(Long id);

    @Query("SELECT o FROM Order o WHERE o.id = :id") // o.id = ?1
    //@Query("SELECT new com.sample.app.model.Order(o.id, o.orderId, o.user) FROM Order o WHERE o.id = :id")
    //@Query(value= "SELECT o.id, o.order_id, o.user_id FRidOM sample.order o WHERE o.id = :id", nativeQuery = true)
    Optional<Order> findByIdByQuery(Long id);
}
