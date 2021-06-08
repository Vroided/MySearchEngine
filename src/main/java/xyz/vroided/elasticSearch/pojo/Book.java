package xyz.vroided.elasticSearch.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author Vroided
 * @date 2021/06/02/7:07 PM
 * @description
 */
@Data
@Document(indexName = "book")
public class Book {
    @Id
    private Long id;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Text)
    private String info;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Long)
    private long price;

    @Field(type = FieldType.Text)
    private String publish;

    @Field(type = FieldType.Text)
    private String type;
}
