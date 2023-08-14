package com.example.cardapio.controller;

import com.example.cardapio.dto.FoodResponseDTO;
import com.example.cardapio.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    @GetMapping
    public ResponseEntity<List<FoodResponseDTO>> getAllFoods() {
        List<FoodResponseDTO> foodResponseDTOList = foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(foodResponseDTOList);
    }
}
