package io.github.assets.app.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FixedAssetItemEVM implements Serializable {

    private static final long serialVersionUID = 7120585086768351439L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String serviceOutletCode;

    @ExcelCell(1)
    private String assetCategoryCode;

    @ExcelCell(2)
    private String assetCategory;

    @ExcelCell(3)
    private String fixedAssetSerialCode;

    @ExcelCell(4)
    private String fixedAssetDescription;

    @ExcelCell(5)
    private String purchaseDate;

    @ExcelCell(6)
    private double purchaseCost;

    @ExcelCell(7)
    private long purchaseTransactionId;

    @ExcelCell(8)
    private long ownershipDocumentId;

}
