package com.zfans.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zfans
 * @date 2020/05/18 20:27
 */
@Data
@NoArgsConstructor
public class CommodityQuery {
    private String keyword;
    private Long brandId;
    private Long classificationId;
    private Long supplierId;
}
