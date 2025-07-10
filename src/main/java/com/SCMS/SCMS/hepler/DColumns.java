package com.SCMS.SCMS.hepler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DColumns {
    private String data;
    private String name;
    private boolean searchable;
    private boolean orderable;
    private DSearch search;

}
