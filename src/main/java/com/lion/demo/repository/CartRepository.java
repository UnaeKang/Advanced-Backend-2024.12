package com.lion.demo.repository;

import com.lion.demo.entity.Cart;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    List<Cart> findByUserUid(String uid);
}
