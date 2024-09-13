package com.js.bookforum.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "리뷰 정보를 나타내는 DTO")
public class ReviewDTO {

    @Schema(description = "리뷰 ID", example = "1")
    private Long reviewId;

    @Schema(description = "작성자 이름", example = "John Doe")
    private String name;

    @Schema(description = "리뷰 작성 일자", example = "2023-01-01")
    private String creationDate;

    @Schema(description = "리뷰 평점", example = "5")
    private int rating;

    @Schema(description = "리뷰 내용", example = "이 책은 정말 훌륭합니다!")
    private String content;

    @Schema(description = "책 ID", example = "1")
    private Long bookId; // 추가된 부분: 리뷰가 속한 책의 ID
}