package com.example.task_schedueler.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;


@Setter
@Getter
@AllArgsConstructor
@Entity
public class Task {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long task_id;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Date dueDate;
    private Timestamp resolvedAt;
    private String title;
    private String description;
    private int priority;
    private String status;

    public Task(){}

    public Task(String title, String description,
                int priority, String status, Date dueDate,
                Timestamp createdAt, Timestamp updatedAt){

        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void process(){
        final SimpleDateFormat timestampFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        final Timestamp nowTimeStampFormatted = Timestamp.valueOf(timestampFormatter.format(Timestamp.from(Instant.now())));

        this.setUpdatedAt(nowTimeStampFormatted);

        /* Task has not passed due*/
        if (this.getDueDate().after(Date.valueOf(LocalDate.now()))){
            this.setResolvedAt(nowTimeStampFormatted);
            this.setStatus("processed");
        } else{
            this.setStatus("passed-due");
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "task_id=" + task_id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", dueDate=" + dueDate +
                ", resolvedAt=" + resolvedAt +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                '}';
    }
}
