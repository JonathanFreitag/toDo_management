package de.mitigant.todo.repository;

import de.mitigant.todo.entity.ToDo;
import de.mitigant.todo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, UUID> {

    Optional<ToDo> findToDoByStatusAndId(Status status,UUID id);

}
