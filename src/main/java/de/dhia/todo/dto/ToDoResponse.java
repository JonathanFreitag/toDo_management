package de.dhia.todo.dto;

import de.dhia.todo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ToDoResponse {

    private String description;
    private Status status;
    private LocalDateTime doneAt;
    private LocalDateTime dueAt;
    private LocalDateTime createdAt;


}
