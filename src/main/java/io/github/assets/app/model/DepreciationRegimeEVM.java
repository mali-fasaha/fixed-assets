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
public class DepreciationRegimeEVM implements Serializable {
    private static final long serialVersionUID = -3967649040086620987L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String assetDecayType;

    @ExcelCell(1)
    private double depreciationRate;

    @ExcelCell(2)
    private String description;
}
