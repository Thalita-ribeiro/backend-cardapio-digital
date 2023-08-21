package com.example.cardapio.controller;

import com.example.cardapio.dto.FoodRequestDto;
import com.example.cardapio.dto.FoodResponseDto;
import com.example.cardapio.entity.Food;
import com.example.cardapio.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public ResponseEntity<List<FoodResponseDto>> getAllFoods() {
        List<FoodResponseDto> foodResponseDTOList = foodRepository.findAll()
                .stream()
                .map(FoodResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(foodResponseDTOList);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<FoodResponseDto> saveFood(@RequestBody FoodRequestDto foodRequestDto) {
        Food food = new Food(foodRequestDto);
        foodRepository.save(food);

        FoodResponseDto foodResponseDTO = new FoodResponseDto(food.getId(), food.getTitle(), food.getImage(), food.getPrice());

        return ResponseEntity.created(URI.create("/food/" + food.getId()))
                .body(foodResponseDTO);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFood(@PathVariable Long id) {
        if (foodRepository.existsById(id)) {
            foodRepository.deleteById(id);
            return ResponseEntity.ok("Food item deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
