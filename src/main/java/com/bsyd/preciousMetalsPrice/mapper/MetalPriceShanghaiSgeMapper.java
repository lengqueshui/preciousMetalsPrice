package com.bsyd.preciousMetalsPrice.mapper;

import com.bsyd.preciousMetalsPrice.model.MetalPriceShanghaiSge;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Mybatis Generator 2020/05/10
 */
@Mapper
public interface MetalPriceShanghaiSgeMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(MetalPriceShanghaiSge record);

    int insertSelective(MetalPriceShanghaiSge record);

    MetalPriceShanghaiSge selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(MetalPriceShanghaiSge record);

    int updateByPrimaryKey(MetalPriceShanghaiSge record);
}