package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StDishevaluation {
    private Integer id;
    private Integer environment;
    private Integer overallevaluation;
    private Integer canteenservice;
    private String content;
    private String uploadpictures;
    private String user;
    private String stDishevaluation;
    private String begintime;
    private String filtercomments;

}
