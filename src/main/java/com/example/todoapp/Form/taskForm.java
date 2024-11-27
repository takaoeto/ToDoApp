package com.example.todoapp.Form;

public class taskForm {
    private Integer id;
    private String title;
    private Integer CategoryId;

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
    public Integer getCategoryId() {
        return CategoryId;
    }
    public void setCategoryId(Integer categoryId) {
        CategoryId = categoryId;
    }
    @Override
    public String toString() {
        return "taskForm [id=" + id + ", title=" + title + ", CategoryId=" + CategoryId + "]";
    }
}
