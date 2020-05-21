package com.zfans.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Zfans
 * @date 2020/05/21 18:37
 */
@Data
@NoArgsConstructor
public class OrderQuery {
    private String keyword;
    private Long customerId;
}
