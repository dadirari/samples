package com.sample.app.repository;

import com.sample.app.model.QUser;
import com.sample.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
        QuerydslPredicateExecutor<User>, QuerydslBinderCustomizer<QUser> {

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {
        //bindings.bind(String.class)
                //.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}
