package xyz.vroided.elasticSearch.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import xyz.vroided.elasticSearch.pojo.Book;

/**
 * @author Vroided
 * @date 2021/06/05/8:07 PM
 * @description
 */
@Data
@Document(indexName = "book")
public class BookDto {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Text)
    private String info;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String publish;

    public BookDto() {
    }

    public BookDto(Book book) {
        this.id = book.getId();
        this.author = book.getAuthor();
        this.info = book.getInfo();
        this.name = book.getName();
        this.publish = book.getPublish();
    }
}
