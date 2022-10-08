package de.mitigant.todo.mapper;

import de.mitigant.todo.dto.ToDoRequest;
import de.mitigant.todo.dto.ToDoResponse;
import de.mitigant.todo.entity.ToDo;
import de.mitigant.todo.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ToDoMapper {

    public ToDo toDoRequestIntializeToToDo(ToDoRequest toDoRequest) {
        ToDo toDo = new ToDo();

        toDo.setDescription(toDoRequest.getDescription());
        toDo.setStatus(Status.NOT_DONE);
        toDo.setCreatedAt(LocalDateTime.now());
        toDo.setDueAt(toDoRequest.getDueAt());

        return toDo;

    }

    public ToDoResponse toDoToToDoResponse(ToDo toDo) {
        ToDoResponse toDoResponse = new ToDoResponse();

        toDoResponse.setDescription(toDo.getDescription());
        toDoResponse.setStatus(toDo.getStatus());
        toDoResponse.setCreatedAt(toDo.getCreatedAt());
        toDoResponse.setDueAt(toDo.getDueAt());
        toDoResponse.setDoneAt(toDo.getDoneAt());

        return toDoResponse;

    }

}