package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StBlacklist {
    private int id;
    private String releaseTime;
    private String relieveTime;
    private String reason;
    private String issued;
    private String enclosure;
    private int supplierId;
}
