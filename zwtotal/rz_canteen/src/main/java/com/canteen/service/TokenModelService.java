package com.canteen.service;

import com.canteen.entity.TokenModel;

public interface TokenModelService {

    //营业流水
    String openWater(TokenModel tokenModel);

    //营业流水明细
    String openWaterInfo(TokenModel tokenModel);


}
