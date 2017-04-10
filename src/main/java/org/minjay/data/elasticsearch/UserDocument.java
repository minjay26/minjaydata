package org.minjay.data.elasticsearch;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
@Document(indexName = "minjay", type = "user")
public class UserDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Field(type= FieldType.Integer, index= FieldIndex.not_analyzed)
    private int userId;
    @Field(type= FieldType.String, index= FieldIndex.not_analyzed)
    private String username;

    @Field(type= FieldType.Integer, index= FieldIndex.not_analyzed)
    private int age;
    
    public UserDocument() {}

    public UserDocument(int userId, String username, int age) {
        this.userId = userId;
        this.username = username;
        this.age = age;
    }

    public long getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
