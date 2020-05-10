package com.bsyd.preciousMetalsPrice.mapper;

import com.bsyd.preciousMetalsPrice.model.MetalPriceI001;
import org.apache.ibatis.annotations.Mapper;

/**
* Created by Mybatis Generator 2020/05/10
*/
@Mapper
public interface MetalPriceI001Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MetalPriceI001 record);

    int insertSelective(MetalPriceI001 record);

    MetalPriceI001 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MetalPriceI001 record);

    int updateByPrimaryKey(MetalPriceI001 record);
}