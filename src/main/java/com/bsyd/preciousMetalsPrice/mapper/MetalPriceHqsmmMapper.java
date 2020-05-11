package com.bsyd.preciousMetalsPrice.mapper;

import com.bsyd.preciousMetalsPrice.model.MetalPriceHqsmm;
import org.apache.ibatis.annotations.Mapper;

/**
* Created by Mybatis Generator 2020/05/11
*/
@Mapper
public interface MetalPriceHqsmmMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(MetalPriceHqsmm record);

    int insertSelective(MetalPriceHqsmm record);

    MetalPriceHqsmm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MetalPriceHqsmm record);

    int updateByPrimaryKey(MetalPriceHqsmm record);
}