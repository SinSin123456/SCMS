package com.SCMS.SCMS.hepler;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResDatatableParam<T> {
    private int draw;
    private int recordsTotal; 
    private int recordsFiltered; 
    private List<T> data;
}