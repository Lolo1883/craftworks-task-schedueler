package com.example.task_schedueler.controller;

import com.example.task_schedueler.dto.TaskDTO;
import com.example.task_schedueler.exception.ResourceNotFoundException;
import com.example.task_schedueler.mapping.TaskJPAToTaskDTOMapper;
import com.example.task_schedueler.model.Task;
import com.example.task_schedueler.service.ITaskService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private static final Logger logger = Logger.getLogger("Tasks.REST.Controller");
    private final ITaskService taskService;
    private final TaskJPAToTaskDTOMapper toTaskDTOMapper;

    @Autowired
    public TaskController(ITaskService service) {
        this.taskService = service;
        toTaskDTOMapper = new TaskJPAToTaskDTOMapper(service);
    }

    @GetMapping("/tasks")
    public List<TaskDTO> getAllTasks() {
        return toTaskDTOMapper.getAllTasks();
    }

    @GetMapping("/tasks/process")
    public ResponseEntity<String> fetchAndProcessAllTasks() {
        List<Task> listUnproccessedTasks = taskService.fetchUnprocessedTasks();

        int countUnprocessed = listUnproccessedTasks.size();

        for (Task task : listUnproccessedTasks) {
            task.process();
            taskService.updateTask(task.getTask_id(), task);
            countUnprocessed -= 1;
            logger.info("task - " + task.getTitle() + " processed successfully");
        }

        int countProcessed = listUnproccessedTasks.size() - countUnprocessed;
        String processingResult = "------------------Processing Results------------------" +
                "\n" +
                "No. of Processed Tasks: " +
                countProcessed +
                "\n" +
                "No. of Left out Tasks: " +
                countUnprocessed +
                "\n" +
                "------------------END Processing Results------------------";

        return ResponseEntity.ok().body(processingResult);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable(value = "id") Long taskId) throws ResourceNotFoundException {
        Optional<Task> taskOptional = taskService.findTaskById(taskId);

        if (taskOptional.isPresent()) {
            return ResponseEntity.ok().body(toTaskDTOMapper.convertDataIntoDTO(taskOptional.get()));
        } else {
            throw new ResourceNotFoundException("Task not found for this id :: " + taskId);
        }
    }

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> createTask(@NotNull @RequestBody Task task) {
        Task newTaskCreated = taskService.createTask(task);
        return ResponseEntity.ok().body(toTaskDTOMapper.convertDataIntoDTO(newTaskCreated));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable(value = "id") Long taskId,
                                              @NotNull @RequestBody Task taskDetails) throws ResourceNotFoundException {
        Optional<Task> taskOptional = taskService.updateTask(taskId, taskDetails);

        if (taskOptional.isPresent()) {
            return ResponseEntity.ok().body(toTaskDTOMapper.convertDataIntoDTO(taskOptional.get()));
        } else {
            throw new ResourceNotFoundException("Task not found to update for this id :: " + taskId);
        }
    }

    @DeleteMapping("/tasks/{id}")
    public Map<String, Boolean> deleteTask(@PathVariable(value = "id") Long taskId) {
        Map<String, Boolean> response = taskService.deleteTask(taskId);
        return response;
    }

    @DeleteMapping("/tasks/")
    public Map<String, Boolean> deleteTask() {
        Map<String, Boolean> response = taskService.deleteAllTasks();
        return response;
    }
}
