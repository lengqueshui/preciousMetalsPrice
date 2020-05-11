package com.bsyd.preciousMetalsPrice.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
* Created by Mybatis Generator 2020/05/11
*/
@Data
public class MetalPriceHqsmm {
    /**
	* 自增ID
	*/
    private Integer id;

    /**
	* 时间
	*/
    private Date date;

    /**
	* 金属名称
	*/
    private String name;

    /**
	* 价格范围
	*/
    private String priceScope;

    /**
	* 平均范围
	*/
    private BigDecimal priceAverage;

    /**
	* 涨跌
	*/
    private BigDecimal updown;

    /**
	* 单位
	*/
    private String units;

    /**
	* 日期
	*/
    private String priceDate;

    /**
	* 创建时间
	*/
    private Date createdAt;

    /**
	* 更新时间
	*/
    private Date updatedAt;
}