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
public class FixedAssetCategoryEVM implements Serializable {
    private static final long serialVersionUID = -4628315315347163293L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String categoryCode;

    @ExcelCell(1)
    private String categoryName;

    @ExcelCell(2)
    private String categoryDescription;

    @ExcelCell(3)
    private long depreciationRegimeId;
}
