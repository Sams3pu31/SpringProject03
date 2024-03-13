package com.myproject.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookCreateDto {
    @Size(min = 3, max = 10)
    @NotBlank(message = "название укажи")
    private String name;
    @NotBlank(message = "жанр не забудь")
    private Long genreId;
}

