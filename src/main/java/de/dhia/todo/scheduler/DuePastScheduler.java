package de.dhia.todo.scheduler;

import de.dhia.todo.repository.ToDoRepository;
import de.dhia.todo.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
public class DuePastScheduler {

    @Autowired
    private ToDoRepository toDoRepository;

    /**
     * change to PAST_DUE for all the items that their due date-time are passed
     */
    @Transactional
    public void changeStatusToPastDue() {
        LocalDateTime now = LocalDateTime.now();
        toDoRepository.findAll().stream().filter(item -> item.getDueAt().isBefore(now)).forEach(item -> {
            item.setStatus(Status.PAST_DUE);
            toDoRepository.save(item);

        });

    }

    /**
     * cron method to execute {@link DuePastScheduler#changeStatusToPastDue()} each 10 seconds
     */
    @Scheduled(cron = "*/10 * * * * *")
    public void schedule() {
        changeStatusToPastDue();
    }

}
