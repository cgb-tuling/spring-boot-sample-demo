package org.example;

import org.example.dynamic.DynamicDataSourceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(classes = DynamicDataSourceApplication.class)
public class JdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void addBook() {
        for (int i = 1; i <= 10; i++) {
            String sql = "INSERT INTO `book`(`id`, `book_name`, `author`, `description`, `price`, `stock`, `update_date`, `create_date`) VALUES (" + i++ + ", 'book1', '1', '1', 1.00, 1, '2022-01-11 10:54:33', '2022-01-11 10:54:35')";
            jdbcTemplate.execute(sql);
        }
    }

}
