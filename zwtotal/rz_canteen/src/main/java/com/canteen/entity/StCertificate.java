package com.canteen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StCertificate {
    private Integer id;
    private String person;
    private String entryName;
    private String scale;
    private String scope;
    private String period;
    private String files;
    private Integer supplierId;
}
