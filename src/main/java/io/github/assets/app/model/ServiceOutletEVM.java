package io.github.assets.app.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ServiceOutletEVM implements Serializable {

    private static final long serialVersionUID = 8846052574242200327L;

    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String serviceOutletCode;

    @ExcelCell(1)
    private String serviceOutletDesignation;

    @ExcelCell(2)
    private String description;

    @ExcelCell(3)
    private String location;
}
