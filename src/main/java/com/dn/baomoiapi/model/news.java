package com.dn.baomoiapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cateId"})
public class news {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String img;
    @Column(name = "createAt")
    private Date createAt;
    @Column(name = "createBy")
    private String createBy;
    private String description;
    @Column(name = "content" )
    private String content;

    @ManyToOne
    @JoinColumn(name="cateId", nullable=false )
    private category cateId;


}
