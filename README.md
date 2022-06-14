# craftworks-task-schedueler


Notes About Solution:

- Tasks created automatically by the schedueler and tasks created by the API (via client) are both persisted in db (storage)

- In order to achieve a queue we need storage and behaviour FIFO.

- We already got the space where tasks from both sources are stored. So we can achieve FIFO by processing the tasks in the creation order and here you go a full queue.

- In the description of this challenge it was mentioned that the processing happening upon calling fetch all endpoint. But i added a seperate endpoint
where all tasks are processed and you get a summary of how many was processed and how many left for any problem (i simulated that in one case only when processing a task passed its due date)


"_You can check the API definition in task-schedueler.yaml_"
