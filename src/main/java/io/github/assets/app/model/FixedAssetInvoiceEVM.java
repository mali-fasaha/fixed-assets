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
public class FixedAssetInvoiceEVM implements Serializable {
    private static final long serialVersionUID = 4589504591791408432L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String invoiceReference;

    @ExcelCell(1)
    private String invoiceDate;

    @ExcelCell(2)
    private double invoiceAmount;

    @ExcelCell(3)
    private boolean isProforma;

    @ExcelCell(4)
    private boolean isCreditNote;

    @ExcelCell(5)
    private long dealerId;

    @ExcelCell(6)
    private String dealerName;
}
