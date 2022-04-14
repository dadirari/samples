package com.sample.app.controller;

import com.querydsl.core.types.Predicate;
import com.sample.app.model.Order;
import com.sample.app.service.SampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {

    private final SampleService sampleService;

    @GetMapping("/create")
    public void create() {
        this.sampleService.createUser();
    }

    @GetMapping("/order/{id}")
    public void order(@PathVariable("id") final Long id) {
        this.sampleService.doOrder(id);
    }

    @GetMapping("/select-order")
    public Page<Order> selectOrder(@RequestParam(required = false) final Map<String, String> maps, final Pageable pageable) {
        return this.sampleService.selectOrder(maps, pageable);
    }

    @GetMapping("/selects")
    public Map<String, Page<Order>> selects(@QuerydslPredicate(root = Order.class) final Predicate predicate, final Pageable pageable) {
        return this.sampleService.selects(predicate, pageable);
    }

    @GetMapping("/select/{id}")
    public Map<String, Order> select(@PathVariable("id") final Long id) {
        return this.sampleService.select(id);
    }
}
