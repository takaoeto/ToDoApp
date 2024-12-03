package com.example.todoapp.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.todoapp.Domain.Category;

@Repository
public class CategoryRepository {
    
    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs, i) -> {
        Category category = new Category();
        category.setId(rs.getInt("id"));
        category.setCategory(rs.getString("category"));
        return category;
    };

    public Category findById(Integer id) {
        String sql = """
                    SELECT id, category
                    FROM category
                    WHERE id=:id
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id",id);
        Category category = template.queryForObject(sql, param, CATEGORY_ROW_MAPPER);
        return category;
    }
    
}
