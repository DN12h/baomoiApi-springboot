package com.dn.baomoiapi.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor
public class newsDto {
    private int id;
    private String title;
    private Date createAt;
    private String img;
    private String createBy;
    private String description;
    private int cateId;


}
