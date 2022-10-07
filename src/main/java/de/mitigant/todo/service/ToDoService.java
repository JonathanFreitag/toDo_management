package de.mitigant.todo.service;

import static java.util.Optional.ofNullable;

import de.mitigant.todo.entity.ToDo;
import de.mitigant.todo.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public ToDo addItem(ToDo toDo) {
        return ofNullable(toDo).map(toDoRepository::save)
                               .orElseThrow(() -> {
                                   log.error("cannot create item" + toDo);
                                   return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create "
                                           + "Item!");
                               });

    }


    public ToDo updateDescription(UUID id, String description) {

        return toDoRepository.findById(id)
                             .map(item->item.setDescription(description))
                             .map(toDoRepository::save)
                             .orElseThrow(() -> {
            log.error(" Can't update item with id = " + id);
            return new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                    "Can't update item");
        });

    }

    public ToDo getSpecificItem(UUID id) {
        return toDoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Item not found."));
    }


}
