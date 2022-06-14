package com.example.task_schedueler.mapping;

import com.example.task_schedueler.dto.TaskDTO;
import com.example.task_schedueler.model.Task;
import com.example.task_schedueler.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TaskJPAToTaskDTOMapper {

    private final ITaskService taskService;
    private final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Autowired
    public TaskJPAToTaskDTOMapper(ITaskService taskService) {
        this.taskService = taskService;
    }

    public List<TaskDTO> getAllTasks(){
        return taskService.fetchAllTasks().stream().map(this::convertDataIntoDTO).collect(Collectors.toList());
    }

    public TaskDTO convertDataIntoDTO(Task task){
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getTask_id());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setResolvedAt(task.getResolvedAt());
        taskDTO.setPriority(getPriorityString(task.getPriority()));
        taskDTO.setDescription(task.getDescription());
        taskDTO.setCreatedAt(Timestamp.valueOf(timestampFormatter.format(task.getCreatedAt())));
        taskDTO.setUpdatedAt(Timestamp.valueOf(timestampFormatter.format(task.getUpdatedAt())));
        taskDTO.setDueDate(task.getDueDate());

        return taskDTO;
    }

    private String getPriorityString(int id){
        switch (id){
            case 1:
                return "HIGH";
            case 2:
                return "MEDIUM";
            case 3:
                return "LOW";
            default: return "N/A";
        }
    }
}
