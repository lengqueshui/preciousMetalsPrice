package com.bsyd.preciousMetalsPrice.model;

import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
* Created by Mybatis Generator 2020/05/10
*/
@Data
public class MetalPriceI001 {
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
	* 品种
	*/
    private String type;

    /**
	* 最高价
	*/
    private BigDecimal maxQuotationPrice;

    /**
	* 最低价
	*/
    private BigDecimal minQuotationPrice;

    /**
	* 涨跌
	*/
    private BigDecimal updown;

    /**
	* 今日综合价格
	*/
    private BigDecimal dayPrice;

    /**
	* 创建时间
	*/
    private Date createdAt;

    /**
	* 更新时间
	*/
    private Date updatedAt;
}