package de.mitigant.todo.controller;

import de.mitigant.todo.entity.ToDo;
import de.mitigant.todo.service.ToDoService;
import de.mitigant.todo.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/item")
public class ToDoController {

    @Autowired
    private ToDoService toDoService;

    @PostMapping
    @ResponseBody
    public ToDo create(@RequestBody ToDo item) {
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

    @PatchMapping("status/done/{id}")
    @ResponseBody
    public ToDo markItemAsDone(@PathVariable("id") UUID id){
        return toDoService.markItemAsStatusDone(id);
    }

    @PatchMapping("status/notdone/{id}")
    @ResponseBody
    public ToDo markItemAsNotDone(@PathVariable("id") UUID id){
        return toDoService.markItemAsStatusNotDone(id);

    }


}
