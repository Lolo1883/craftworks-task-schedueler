package com.example.task_schedueler.schedueler;

import com.example.task_schedueler.model.Task;
import com.example.task_schedueler.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;
import java.util.logging.Logger;

@Service
@EnableAsync
public class ScheduelerService {

    private static int autoTaskNo = 1;
    private final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private static final Logger log = Logger.getLogger("Tasks.Auto.ScheduelerService");
    private ITaskService taskService;

    @Autowired
    public ScheduelerService(ITaskService taskService) {
        this.taskService = taskService;
    }


    @Async
    @Scheduled(fixedDelay = 15000)
    public void scheduleFixedDelayTask() throws InterruptedException {
        Random rand = new Random();

        /* will assume that we add random due date of 1 or 2 days from the date of task creation*/
        int randomDue = rand.nextInt((2 - 1) + 1) + 1;

        Calendar cal = Calendar.getInstance();
        cal.setTime(Date.valueOf(LocalDate.now()));
        cal.add(Calendar.DATE, randomDue);

        Task newTask  = taskService.createTask(new Task(
                "Auto Task &" + autoTaskNo,
                "Automatic task by the scheduler",
                2,
                "new",
                new Date(cal.getTimeInMillis()),
                Timestamp.valueOf(timestampFormatter.format(Timestamp.from(Instant.now()))),
                Timestamp.valueOf(timestampFormatter.format(Timestamp.from(Instant.now())))));
        log.info(
                "Task no: " + autoTaskNo + " created successfully with due: "
                        + newTask.getDueDate() + " [createdAt: " + newTask.getCreatedAt()
                        + ", updatedAt: " + newTask.getUpdatedAt()+ "]");

        ScheduelerService.autoTaskNo += 1;
    }
}
