package com.example.es.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @Author Artist
 * @Description
 * @Date 2023/8/14
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(indexName = "wolf2w_userinfo", type = "userinfo")
public class UserInfoEs {

    public static final String INDEX_NAME = "wolf2w_userinfo";
    public static final String TEXT_NAME = "userinfo";

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String nickname;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String city;
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String info;
}
