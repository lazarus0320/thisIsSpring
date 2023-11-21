package com.example.thisisspring.controller;

import com.example.thisisspring.dto.CoffeeBeanDto;
import com.example.thisisspring.dto.UpdateCoffeeBeanDto;
import com.example.thisisspring.repository.CoffeeBeanRepository;
import com.example.thisisspring.service.CoffeeBeanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자", description = "커피 재고 관리자 API")
@RestController
@RequestMapping("/coffee")
public class CoffeeBeanController {

    private final CoffeeBeanService coffeeBeanService;
    private final CoffeeBeanRepository coffeeBeanRepository;

    @Autowired
    public CoffeeBeanController(CoffeeBeanService coffeeBeanService,
                                CoffeeBeanRepository coffeeBeanRepository) {
        this.coffeeBeanService = coffeeBeanService;
        this.coffeeBeanRepository = coffeeBeanRepository;
    }

    @Operation(
            summary = "커피 데이터 생성",
            description = "10개의 커피 임시 데이터 생성",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공")
            }
    )
    @PostMapping("/create")
    public String createCoffeeBeans() {
        coffeeBeanService.saveTenCoffeeBeansEfficient();
        return "10개의 커피 데이터가 생성되었습니다.";
    }

    @Operation(
            summary = "커피 데이터 조회",
            description = "모든 커피 데이터 전체 조회",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "204", description = "조회할 데이터가 없음")
            }
    )
    @GetMapping("/list")
    public ResponseEntity<List<CoffeeBeanDto>> getAllCoffeeBeans() {
        List<CoffeeBeanDto> coffeeBeansDto = coffeeBeanService.getAllCoffeeBeansDto();

        if (coffeeBeansDto.isEmpty()) {
            // 만약 데이터가 없을 경우
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            // 데이터가 있을 경우
            return new ResponseEntity<>(coffeeBeansDto, HttpStatus.OK);
        }
    }


    @Operation(
            summary = "커피 데이터 삭제",
            description = "단일 커피 데이터 삭제",
            responses = {
                    @ApiResponse(responseCode = "200", description = "성공"),
                    @ApiResponse(responseCode = "404", description = "해당 ID의 커피 데이터를 찾을 수 없습니다.")
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCoffeeBeanById(
            @Parameter(description = "삭제할 항목의 id값", example = "1", required = true)
            @PathVariable(name = "id") Long id
    ) {
        if (coffeeBeanRepository.existsById(id)) { // 해당 id 값의 데이터가 있는 경우 처리
            coffeeBeanRepository.deleteById(id);
            return new ResponseEntity<>("커피 데이터가 삭제되었습니다.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 ID의 커피 데이터를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
    }


    @Operation(
            summary = "커피 데이터 재고 추가",
            description = "단일 커피 데이터 재고 추가",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "단일 커피 데이터 재고 추가 완료"
                    )
            }
    )
    @PutMapping("/update")
    public ResponseEntity<String> updateCoffeeBeanQuantity(@RequestBody UpdateCoffeeBeanDto coffeeBeanDto) {
        try {
            coffeeBeanService.updateCoffeeBeanQuantity(coffeeBeanDto.getName(), coffeeBeanDto.getQuantity());
            return ResponseEntity.ok("커피 데이터의 재고가 업데이트 되었습니다.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("커피 데이터 재고 업데이트 중에 오류가 발생했습니다.");
        }
    }
}
