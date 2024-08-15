package com.ribeiro.BtgPactualChallenge.repository;

import com.ribeiro.BtgPactualChallenge.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<OrderEntity, Long> {

    Page<OrderEntity> findAllByCostumerId(Long id, PageRequest pageRequest);
}
