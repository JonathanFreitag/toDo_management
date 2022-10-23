package de.dhia.todo.controller;

import de.dhia.todo.dto.ToDoRequest;
import de.dhia.todo.service.ToDoService;
import de.dhia.todo.entity.ToDo;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@OpenAPIDefinition(info = @Info(title = "TODO API", description = "TODO Information"))
@RequestMapping(value = "/api/v1/item")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping
    @ResponseBody
    public ToDo create(@RequestBody ToDoRequest item) {
        return toDoService.addItem(item);

    }

    @PatchMapping("/{id}")
    @ResponseBody
    public ToDo update(@PathVariable("id") UUID id, @RequestParam String description) {
        return toDoService.updateDescription(id, description);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ToDo getItem(@PathVariable("id") UUID id) {
        return toDoService.getSpecificItem(id);
    }

    @PatchMapping("/status/done/{id}")
    @ResponseBody
    public ToDo markItemAsDone(@PathVariable("id") UUID id){
        return toDoService.markItemAsStatusDone(id);
    }

    @PatchMapping("/status/notdone/{id}")
    @ResponseBody
    public ToDo markItemAsNotDone(@PathVariable("id") UUID id, @RequestParam("newduedate") @DateTimeFormat(iso =
            DateTimeFormat.ISO.DATE_TIME) @Parameter(example="2022-10-08T20:45:59.002Z") LocalDateTime
             newDueDate){
        return toDoService.markItemAsStatusNotDone(id, newDueDate);

    }

    @GetMapping("/status/notdone")
    @ResponseBody
    public List<ToDo> getAllItemsNotDone(@RequestParam @Parameter(description="true : get items are not done, false: "
            + "get all items") Boolean option) {
        if (option) {
            return toDoService.getAllItemsAreNotDone();
        } else {
            return toDoService.getAllItems();
        }

    }


}
