package com.lion.demo.repository;

import com.lion.demo.entity.Restaurant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends ElasticsearchRepository<Restaurant, String> {

}

