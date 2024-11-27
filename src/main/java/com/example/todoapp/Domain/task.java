package com.example.todoapp.Domain;

public class Task {
    private Integer id;
    private String title;
    private Category category;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return "Task [id=" + id + ", title=" + title + ", category=" + category + "]";
    }
}
