package com.entity;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
@Where(clause = "enable_flag=1")
public class User extends ModifiedEntity {

    @Column(name = "name")
    private String name;

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
