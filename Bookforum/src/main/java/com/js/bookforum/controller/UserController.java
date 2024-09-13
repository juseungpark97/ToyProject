package com.js.bookforum.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.js.bookforum.dto.RentalDTO;
import com.js.bookforum.entity.Rental;
import com.js.bookforum.entity.User;
import com.js.bookforum.service.RentalService;
import com.js.bookforum.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User API", description = "사용자 관리 API")
public class UserController {

    private final UserService userService;
    private final RentalService rentalService;

    @Operation(summary = "새 사용자 생성", description = "새로운 사용자를 생성합니다.")
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @Operation(summary = "사용자 조회", description = "ID에 해당하는 사용자 정보를 조회합니다.")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "모든 사용자 조회(관리자 제외)", description = "등록된 모든 사용자의 정보를 조회합니다.")
    @GetMapping
    public List<User> getAllUsers() {
        // 관리자(ADMIN) 역할을 가진 사용자를 제외하고 반환
        return userService.getAllUsers().stream()
                          .filter(user -> !user.getRole().getName().equalsIgnoreCase("ADMIN"))
                          .collect(Collectors.toList());
    }

    @Operation(summary = "사용자 정보 수정", description = "ID에 해당하는 사용자의 정보를 수정합니다.")
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @Operation(summary = "사용자 삭제", description = "ID에 해당하는 사용자를 삭제합니다.")
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
    
    @Operation(summary = "특정 사용자의 대여 기록 조회", description = "특정 사용자 ID에 대한 대여 기록을 페이징하여 조회합니다.")
    @GetMapping("/{userId}/rentals")
    public Page<RentalDTO> getUserRentals(
        @PathVariable Long userId,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "5") int size) {
        
        Page<Rental> rentals = rentalService.findRentalsByUserId(userId, PageRequest.of(page, size));
        return rentals.map(rental -> new RentalDTO(
        	rental.getRentalId(),
            rental.getBook().getBookId(),
            rental.getBook().getTitle(),
            rental.getBook().getAuthor(),
            rental.getRentalDate(),
            rental.getReturnDate(),
            rental.getUser().getName()
        ));
    }
    
    @Operation(summary = "특정 사용자 조", description = "이메일로 사용자를 조회합니다.")
    @GetMapping("/email/{userEmail}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String userEmail) {
        User user = userService.findByEmail(userEmail);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }
    
    
}