package com.example.task_schedueler.service;

import com.example.task_schedueler.model.Task;
import com.example.task_schedueler.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements ITaskService {

    private TaskRepository taskRepository;

    private final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    public TaskServiceImpl(TaskRepository repository){
        super();
        this.taskRepository = repository;
    }

    public List<Task> fetchAllTasks(){
        return taskRepository.findAll(Sort.by(Sort.Direction.ASC, "createdAt"));
    }

    public List<Task> fetchUnprocessedTasks(){
        return taskRepository
                .findAll(Sort.by(Sort.Direction.ASC, "createdAt"))
                .stream()
                .filter(task -> task.getResolvedAt() == null)
                .collect(Collectors.toList());
    }

    public Optional<Task> findTaskById(long id){
        return taskRepository.findById(id);
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(long id, Task taskDetails){
        Optional<Task> taskOptional = taskRepository.findById(id);
        if(taskOptional.isPresent()){
            Task task = taskOptional.get();
            task.setTitle(taskDetails.getTitle());
            task.setDescription(taskDetails.getDescription());
            task.setPriority(taskDetails.getPriority());
            task.setStatus(taskDetails.getStatus());
            task.setDueDate(task.getDueDate());
            task.setResolvedAt(task.getResolvedAt());
            task.setUpdatedAt(Timestamp.valueOf(timestampFormatter.format(Timestamp.from(Instant.now()))));
            task.setCreatedAt(taskDetails.getCreatedAt());

            final Task updatedTask = taskRepository.save(task);
            return Optional.of(updatedTask);
        }
        return taskOptional;
    }

    public Map<String, Boolean> deleteTask(long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        Map<String, Boolean> result = new HashMap<>();

        if (taskOptional.isPresent()) {
            taskRepository.delete(taskOptional.get());
            result.put("deleted", true);
        } else{
            result.put("deleted", false);
        }
        return result;
    }

    @Override
    public Map<String, Boolean> deleteAllTasks() {
        taskRepository.deleteAll();
        Map<String, Boolean> result = new HashMap<>();
        result.put("deleted-all", true);

        return result;
    }

}
