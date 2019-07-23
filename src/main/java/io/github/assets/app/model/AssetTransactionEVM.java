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
public class AssetTransactionEVM implements Serializable {
    private static final long serialVersionUID = -1568404038442038972L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String transactionReference;

    @ExcelCell(1)
    private String transactionDate;

    @ExcelCell(2)
    private long scannedDocumentId;
}
