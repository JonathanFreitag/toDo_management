package de.dhia.todo.repository;

import de.dhia.todo.entity.ToDo;
import de.dhia.todo.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, UUID> {

    Optional<ToDo> findToDoByStatusAndId(Status status,UUID id);

}
