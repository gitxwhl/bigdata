package com.canteen.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class NutritionalAnalysisVo { //
   private int id;

   private String name;

   private String meals;

   private String energy;
}
