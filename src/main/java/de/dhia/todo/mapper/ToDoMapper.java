package de.dhia.todo.mapper;

import de.dhia.todo.dto.ToDoRequest;
import de.dhia.todo.entity.ToDo;
import de.dhia.todo.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ToDoMapper {

    public ToDo toDoRequestIntializeToToDo(ToDoRequest toDoRequest) {
        if (toDoRequest == null) {
            return null;
        }
        ToDo toDo = new ToDo();

        toDo.setDescription(toDoRequest.getDescription());
        toDo.setStatus(Status.NOT_DONE);
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setDueAt(toDoRequest.getDueAt());

        return toDo;

    }


}