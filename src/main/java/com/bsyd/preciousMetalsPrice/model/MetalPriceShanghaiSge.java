package com.bsyd.preciousMetalsPrice.model;

import java.util.Date;

import lombok.*;

/**
* Created by Mybatis Generator 2020/05/10
*/
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetalPriceShanghaiSge {
    /**
	* 自增ID
	*/
    private Integer id;

    /**
	* 渠道类型
	*/
    private Date date;

    /**
	* 金属名称
	*/
    private String name;

    /**
	* 开盘价
	*/
    private Long openingQuotationPrice = 0L;

    /**
	* 收盘价
	*/
    private Long closingQuotationPrice = 0L;

    /**
	* 最高价
	*/
    private Long maxQuotationPrice = 0L;

    /**
	* 最低价
	*/
    private Long minQuotationPrice = 0L;

    /**
	* 创建时间
	*/
    private Date createdAt;

    /**
	* 更新时间
	*/
    private Date updatedAt;
}