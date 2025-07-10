package com.SCMS.SCMS.hepler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReqDatatableParam {
    private int draw;
    private List<DColumns> columns;
    private List<DOrder> order;
    private int start;
    private int length;
    private DSearch search; 
}
