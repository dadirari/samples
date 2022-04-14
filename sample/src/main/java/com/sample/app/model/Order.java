package com.sample.app.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`order`")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
//@NamedEntityGraph(name = "graph.OrderWithOrderItem", attributeNodes = @NamedAttributeNode(value = "orderItems", subgraph = "subgraph.orderItems"),
        //subgraphs = {@NamedSubgraph(name = "subgraph.orderItems", attributeNodes = @NamedAttributeNode(value = "item", subgraph = "subgraph.item"))})
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}