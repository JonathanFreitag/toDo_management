package de.mitigant.todo.service;

import static java.util.Optional.ofNullable;

import de.mitigant.todo.dto.ToDoRequest;
import de.mitigant.todo.dto.ToDoResponse;
import de.mitigant.todo.entity.ToDo;
import de.mitigant.todo.mapper.ToDoMapper;
import de.mitigant.todo.repository.ToDoRepository;
import de.mitigant.todo.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoMapper toDoMapper;
    private final ToDoRepository toDoRepository;

    /**
     * create new item, initialized to NOT_DONE as status
     * @param toDoRequest is ToDo Request body
     * @return toDo Response
     * @exception ResponseStatusException if the request is bad
     */
    public ToDoResponse addItem(ToDoRequest toDoRequest) {
        return ofNullable(toDoRequest).map(toDoMapper::toDoRequestIntializeToToDo)
                               .map(toDoRepository::save)
                               .map(toDoMapper::toDoToToDoResponse)
                               .orElseThrow(() -> {
                                   log.error("cannot create item" + toDoRequest);
                                   return new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot create "
                                           + "Item!");
                               });

    }

    /**
     * update description for an item
     * @param id unique identifier of the item needs to be update
     * @param description item description
     * @return toDo Response
     * @exception ResponseStatusException if the request is not acceptable
     */
    public ToDoResponse updateDescription(UUID id, String description) {

        return toDoRepository.findById(id)
                             .map(item -> item.setDescription(description))
                             .map(toDoRepository::save)
                             .map(toDoMapper::toDoToToDoResponse)
                             .orElseThrow(() -> {
                                 log.error(" Can't update item with id = " + id);
                                 return new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE,
                                         "Can't update item");
                             });

    }

    /**
     * get Details for a specific item
     * @param id unique identifier of the item
     * @return toDo Response
     * @exception ResponseStatusException if the Request is bed
     */
    public ToDoResponse getSpecificItem(UUID id) {
        return toDoRepository.findById(id)
                             .map(toDoMapper::toDoToToDoResponse)
                             .orElseThrow(
                                     () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                             "Item not found."));
    }

    /**
     * Mark and item as done when it's not done yet, and set done date-time
     * @param id unique identifier of the item
     * @return toDo response
     */
    public ToDoResponse markItemAsStatusDone(UUID id) {

        ToDo itemFound = toDoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Item not found."));
        if (itemFound.getStatus().toString().equals(Status.NOT_DONE.toString())) {
            itemFound.setStatus(Status.DONE);
            itemFound.setDoneAt(LocalDateTime.now());
            ToDo toDoUpdated= toDoRepository.save(itemFound);
           return  toDoMapper.toDoToToDoResponse(toDoUpdated);
        } else {
            log.trace("can't change status to Done for this Item = " + id);
            return null;
        }


    }

    /**
     * Mark item as not Done either it's done or not done, and update due date-time
     * @param id unique identifier of the item
     * @param newdueDate date-time of new due date
     * @return toDo Response
     *
     */
    public ToDoResponse markItemAsStatusNotDone(UUID id, LocalDateTime newdueDate) {
        ToDo itemFound = toDoRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Item not found."));

        switch (itemFound.getStatus().toString()) {
            case "DONE":

            case "PAST_DUE":
                itemFound.setStatus(Status.NOT_DONE);
                itemFound.setDoneAt(null);
                itemFound.setDueAt(newdueDate);
                return toDoMapper.toDoToToDoResponse(toDoRepository.save(itemFound));

            default:
                log.info("can't change status because it's already not Done = " + id);
                return toDoMapper.toDoToToDoResponse(itemFound);
        }


    }

    /**
     * Get List of items that are not done
     * @return List of toDo items
     */
    public List<ToDo> getAllItemsAreNotDone() {
        return toDoRepository.findAll().stream().filter(item -> item.getStatus() == Status.NOT_DONE)
                             .collect(Collectors.toList());

    }

    /**
     * get all toDo items
     * @return list of toDo items
     */
    public List<ToDo> getAllItems() {
        return toDoRepository.findAll();
    }

}
