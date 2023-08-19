package com.example.cardapio.controller;

import com.example.cardapio.dto.FoodRequestDto;
import com.example.cardapio.dto.FoodResponseDTO;
import com.example.cardapio.entity.Food;
import com.example.cardapio.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<FoodResponseDTO>> getAllFoods() {
        List<FoodResponseDTO> foodResponseDTOList = foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(foodResponseDTOList);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<FoodResponseDTO> saveFood(@RequestBody FoodRequestDto foodRequestDto) {
        Food food = new Food(foodRequestDto);
        foodRepository.save(food);

        FoodResponseDTO foodResponseDTO = new FoodResponseDTO(food.getId(), food.getTitle(), food.getImage(), food.getPrice());

        return ResponseEntity.created(URI.create("/food/" + food.getId()))
                .body(foodResponseDTO);
    }
}
