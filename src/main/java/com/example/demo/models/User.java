package com.example.demo.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
//@Table(name = "users") if we want that model shld be saved with a diff name in database
//mostly used in postgresql because user is a keyword there so cant bw saved like this
public class User extends BaseModel {
    private String name;
    private String email_id;
}
