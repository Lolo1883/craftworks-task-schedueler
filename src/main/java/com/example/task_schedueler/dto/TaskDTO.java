package com.example.task_schedueler.dto;


import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class TaskDTO {
    private long id;
    private String title;
    private String description;
    private String priority;
    private String status;
    private Date dueDate;
    private Date resolvedAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
