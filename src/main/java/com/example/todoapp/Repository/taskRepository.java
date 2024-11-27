package com.example.todoapp.Repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.todoapp.Domain.Category;
import com.example.todoapp.Domain.Task;

@Repository
public class taskRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;
    
    private static final RowMapper<Task> TASK_ROW_MAPPER = (rs, i) -> {
        Task task = new Task();
        task.setId(rs.getInt("t_id"));
        task.setTitle(rs.getString("t_title"));

        Category category = new Category();
        category.setId(rs.getInt("c_id"));
        category.setCategory(rs.getString("c_category"));
        return task;
    };

    public Task findById(Integer id) {
        String sql = """
                SELECT t.id AS t_id, t.title AS t_title, c.id AS c_id, c.category AS c_category
                FROM task AS t
                LEFT OUTER JOIN category AS c
                ON t.category_id = c.id
                WHERE id = :id
                """;
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        Task task = template.queryForObject(sql, param, TASK_ROW_MAPPER);
        return task;
            
    }

    public List<Task> findAll() {
        String sql = """
                SELECT t.id AS t_id, t.title AS t_title, c.id AS c_id, c.category AS c_category
                FROM task AS t
                LEFT OUTER JOIN category AS c
                ON t.category_id = c.id
                WHERE id = :id
                """;
        List<Task> task = template.query(sql, TASK_ROW_MAPPER);
        return task;
    }

    public void insert(Task task) {
        String sql = """
                INSERT INTO task(title, category_id)
                            VALUES(:title, :cateogryId)
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("title", task.getTitle())
                .addValue("categoryId", task.getCategory().getId());
        template.update(sql, param);
    }
    
    public void update(Task task) {
        String sql = """
                UPDATE task
                SET id=:id, title=:title, category_id=:categoryId
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", task.getId())
                .addValue("title", task.getTitle())
                .addValue("categoryId", task.getCategory().getId());
        template.update(sql, param);
    }

    public void delete(Integer id) {
        String sql = """
                DELETE FROM task
                WHERE id=:id
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        template.update(sql, param);
    }

}
