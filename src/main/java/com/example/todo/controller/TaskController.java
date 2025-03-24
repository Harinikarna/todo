package com.example.todo.controller;

import com.example.todo.models.Task;
import com.example.todo.repository.TaskRepository;
import com.example.todo.services.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;
    private final TaskService taskService;

    // Constructor-based dependency injection (preferred over @Autowired for testing & maintainability)
    public TaskController(TaskService taskService, TaskRepository taskRepository) {
        this.taskService = taskService;
        this.taskRepository = taskRepository;
    }

    @GetMapping
    public String getTasks(Model model) {
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);
        return "tasks";
    }

    @PostMapping
    public String addTask(@RequestParam String title) {
        taskService.createTask(title);
        return "redirect:/";
    }

    @PostMapping("/addTask")
    public ResponseEntity<String> addTask(@RequestBody Task task) {
        taskRepository.save(task);
        return ResponseEntity.ok("Task added successfully");
    }

    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id) {
        taskService.toggleTask(id);
        return "redirect:/";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }
}
