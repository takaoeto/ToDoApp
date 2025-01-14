package com.example.todoapp.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapp.Domain.Task;
import com.example.todoapp.Form.TaskForm;
import com.example.todoapp.Service.CategoryService;
import com.example.todoapp.Service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskApiController {
    
    @Autowired
    private TaskService taskService;
    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    public TaskForm setUpTaskForm() {
        return new TaskForm();
    }


    @PostMapping("/tasks")
    public ResponseEntity<Map<String, Object>> insert(@RequestBody Map<String, Object> map) {
        Map<String, Object> response = new HashMap<>();
        String title = (String)map.get("title");
        try {
            Task task = new Task();
            task.setTitle(title);
            Integer categoryId = 1;
            task.setCategory(categoryService.findById(categoryId));
            System.out.println(task.toString());
            taskService.insert(task);
            // System.out.println("あああああああああああああああああああああああああああああああああああああ");
            // System.out.println(task.toString());
            response.put("title", task.getTitle());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "作成処理に失敗しました");
            response.put("error", e.getMessage());  // Optional: 詳細なエラーメッセージ
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/tasks/{categoryId}")
    public ResponseEntity<List<Task>> findByCategoryId(@PathVariable("categoryId") Integer categoryId) {
        try {
            List<Task> taskList = taskService.findByCategoryId(categoryId);
            return ResponseEntity.ok(taskList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    
    @PutMapping("/tasks/{id}")                           
    public ResponseEntity<Map<String, Object>> update(@PathVariable("id") Integer id, @RequestBody Map<String, Object> map) {
        Map<String, Object> response = new HashMap<>();
        try {
            Task task = taskService.findById(id);
    
            String title = (String) map.get("title");
            task.setTitle(title);    
            Integer categoryId = (Integer) map.get("category_id");
            task.setCategory(categoryService.findById(categoryId));
    
            taskService.update(task);
            response.put("task", task);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "更新処理に失敗しました");
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    
    @DeleteMapping("/tasks/{id}")
        public ResponseEntity<Void> deleteTask(@PathVariable("id") Integer id) {
        try {
            taskService.delete(id);
            return ResponseEntity.ok().build(); 
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

