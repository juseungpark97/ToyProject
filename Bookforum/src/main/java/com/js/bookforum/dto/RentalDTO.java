package com.js.bookforum.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RentalDTO {
	private final Long rentalId;
    private final Long bookId;
    private final String title;
    private final String author;
    private final Date rentalDate;
    private final Date returnDate;
    private final String userName;
}