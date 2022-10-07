package de.mitigant.todo.service;

import static java.util.Optional.ofNullable;

import de.mitigant.todo.entity.ToDo;
import de.mitigant.todo.repository.ToDoRepository;
import de.mitigant.todo.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
                             .map(item -> item.setDescription(description))
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

    public ToDo markItemAsStatusDone(UUID id) {

        ToDo itemFound = toDoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Item not found."));
        if (itemFound.getStatus().toString().equals(Status.NOT_DONE.toString())) {
            itemFound.setStatus(Status.DONE);
            return toDoRepository.save(itemFound);
        } else {
            log.trace("can't change status to Done for this Item = " + id);
            return null;
        }

    }

    public ToDo markItemAsStatusNotDone(UUID id) {
        ToDo itemFound = toDoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Item not found."));

        if (itemFound.getStatus().toString().isEmpty() || itemFound.getStatus() == null) {
            itemFound.setStatus(Status.NOT_DONE);
            return toDoRepository.save(itemFound);
        } else {
            log.trace("can't change status to Not Done for this Item = " + id);
            return null;
        }
    }

    public List<ToDo> getAllItemsAreNotDone() {
        return toDoRepository.findAll().stream().filter(item -> item.getStatus() == Status.NOT_DONE)
                             .collect(Collectors.toList());

    }

    public List<ToDo> getAllItems() {
        return toDoRepository.findAll();
    }

}
