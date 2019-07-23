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
public class DealerEVM implements Serializable {
    private static final long serialVersionUID = 3464239289243466052L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String titleTypes;

    @ExcelCell(1)
    private String dealerName;

    @ExcelCell(2)
    private String dealerAddress;

    @ExcelCell(3)
    private String dealerPhoneNumber;

    @ExcelCell(4)
    private String dealerEmail;

    @ExcelCell(5)
    private String bankName;

    @ExcelCell(6)
    private String bankAccountNumber;

    @ExcelCell(7)
    private String bankBranch;

    @ExcelCell(8)
    private String bankSwiftCode;

    @ExcelCell(9)
    private String bankPhysicalAddress;

    @ExcelCell(10)
    private boolean locallyDomiciled;

    @ExcelCell(11)
    private String taxAuthorityRef;
}
