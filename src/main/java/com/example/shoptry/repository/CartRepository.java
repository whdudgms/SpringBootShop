package com.example.shoptry.repository;

import com.example.shoptry.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {


}
