package com.zfans.vo;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Zfans
 * @date 2020/05/17 1:05
 */
@Data
@NoArgsConstructor
public class ClassificationQuery {
    private String keyword;
    private Long superId;
}
