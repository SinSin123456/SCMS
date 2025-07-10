package com.SCMS.SCMS.hepler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResDatatableParam <T> {
    private int draw;
    private int recordTotal;
    private int recordeFiltered;
    private List <T> data;

}
