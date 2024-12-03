package com.lion.demo.repository;

import com.lion.demo.entity.Order;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    // 개별 사용자
    List<Order> findByUserUid(String uid);

    // 관리자 - 기간 설정
    List<Order> findByOrderDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
