package dev.java10x.MagicFridgeAI.repository;

import dev.java10x.MagicFridgeAI.model.Fooditem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FooditemRepository extends JpaRepository<Fooditem, Long> {
}
