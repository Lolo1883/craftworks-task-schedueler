package com.example.task_schedueler.service;

import com.example.task_schedueler.model.Task;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ITaskService {

    public List<Task> fetchAllTasks();
    public List<Task> fetchUnprocessedTasks();
    public Optional<Task> findTaskById(long id);
    public Task createTask(Task task);
    public Optional<Task> updateTask(long id, Task taskDetails);
    public Map<String, Boolean> deleteTask(long id);
    public Map<String, Boolean> deleteAllTasks();
}
