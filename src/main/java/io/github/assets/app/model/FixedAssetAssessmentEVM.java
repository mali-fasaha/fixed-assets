package io.github.assets.app.model;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import io.github.assets.domain.enumeration.AssetCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class FixedAssetAssessmentEVM implements Serializable {
    private static final long serialVersionUID = 798264723205352339L;
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String description;

    @ExcelCell(1)
    private String assetCondition;

    @ExcelCell(2)
    private String assessmentDate;

    @ExcelCell(3)
    private String assessmentRemarks;

    @ExcelCell(4)
    private String nameOfAssessingStaff;

    @ExcelCell(5)
    private String nameOfAssessmentContractor;

    @ExcelCell(6)
    private String currentServiceOutletCode;

    @ExcelCell(7)
    private String currentPhysicalAddress;

    @ExcelCell(8)
    private String nextAssessmentDate;

    @ExcelCell(9)
    private String nameOfUser;

    @ExcelCell(10)
    private long fixedAssetItemId;

    @ExcelCell(11)
    private double estimatedValue;

    @ExcelCell(12)
    private double estimatedUsefulMonths;
}
