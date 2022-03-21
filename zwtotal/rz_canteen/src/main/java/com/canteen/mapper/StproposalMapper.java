package com.canteen.mapper;

import com.canteen.entity.Stpersonnel;
import com.canteen.entity.Stproposal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StproposalMapper {
        /**
         *用餐建议设置
         */
        boolean updateStproposal(Stproposal stproposal);
        /**
         *用餐建议
         */
        List<Stproposal> findStproposal();


}
