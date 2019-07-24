package io.github.assets.app.excel;

import io.github.assets.app.model.AssetTransactionEVM;
import io.github.assets.app.model.DealerEVM;
import io.github.assets.app.model.DepreciationRegimeEVM;
import io.github.assets.app.model.FixedAssetAssessmentEVM;
import io.github.assets.app.model.FixedAssetCategoryEVM;
import io.github.assets.app.model.FixedAssetInvoiceEVM;
import io.github.assets.app.model.FixedAssetItemEVM;
import io.github.assets.app.model.ServiceOutletEVM;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.github.assets.app.excel.ExcelTestUtil.readFile;
import static io.github.assets.app.excel.ExcelTestUtil.toBytes;
import static org.junit.Assert.assertEquals;

@Slf4j
public class ExcelFileUtilsTest {

    @Test
    public void deserializeServiceOutletFile() throws IOException {

        // @formatter:off
        List<ServiceOutletEVM> serviceOutlets =
            ExcelFileUtils
                .deserializeServiceOutletFile(toBytes(readFile("service_outlets.xlsx")));
        // @formatter:on

        log.info("ServiceOutlet # 1: {}", serviceOutlets.get(0));
        log.info("ServiceOutlet # 12: {}", serviceOutlets.get(11));

        assertEquals(1000, serviceOutlets.size());
        assertEquals("5 Springview Park", serviceOutlets.get(0).getLocation());
        assertEquals("Human Resources", serviceOutlets.get(0).getDescription());
        assertEquals("802", serviceOutlets.get(0).getServiceOutletCode());

        assertEquals("17315 Fieldstone Center", serviceOutlets.get(11).getLocation());
        assertEquals("Training", serviceOutlets.get(11).getDescription());
        assertEquals("693", serviceOutlets.get(11).getServiceOutletCode());
    }

    @Test
    public void deserializeAssetTransactionFile() throws IOException {

        // @formatter:off
        List<AssetTransactionEVM> transactions =
            ExcelFileUtils
                .deserializeAssetTransactionFile(toBytes(readFile("asset_transactions.xlsx")));
        // @formatter:on

        assertEquals(1000, transactions.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", transactions.get(0).getTransactionReference());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", transactions.get(0).getTransactionDate());
        assertEquals("Wrong Scanned document! : ", 555, transactions.get(0).getScannedDocumentId());
    }

    @Test
    public void deserializeDealerFile() throws IOException {
        // @formatter:off
        List<DealerEVM> dealers =
            ExcelFileUtils
                .deserializeDealerFile(toBytes(readFile("dealers.xlsx")));
        // @formatter:on

        assertEquals(1000, dealers.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", dealers.get(0).getBankAccountNumber());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", dealers.get(0).getDealerName());
        assertEquals("Wrong Scanned document! : ", 555, dealers.get(0).getBankName());
    }

    @Test
    public void deserializeDepreciationRegimeFile() throws IOException {

        // @formatter:off
        List<DepreciationRegimeEVM> excelFileItems =
            ExcelFileUtils
                .deserializeDepreciationRegimeFile(toBytes(readFile("depreciation_regime.xlsx")));
        // @formatter:on

        assertEquals("Wron # of records! : ", 5, excelFileItems.size());
        assertEquals("Wrong Depreciation Regime! : ", "STRAIGHT_LINE", excelFileItems.get(0).getAssetDecayType());
        assertEquals("Wrong Depreciation Rate! : ", 0.03, excelFileItems.get(0).getDepreciationRate(), 0.001);
        assertEquals("Wrong Description! : ", "Straight line depreciation", excelFileItems.get(0).getDescription());
    }

    @Test
    public void deserializeFixedAssetAssessmentFile() throws IOException {
        // @formatter:off
        List<FixedAssetAssessmentEVM> assessments =
            ExcelFileUtils
                .deserializeFixedAssetAssessmentFile(toBytes(readFile("asset_assessment.xlsx")));
        // @formatter:on

        assertEquals(1000, assessments.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", assessments.get(0).getAssetCondition());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", assessments.get(0).getCurrentServiceOutletCode());
        assertEquals("Wrong Scanned document! : ", 555, assessments.get(0).getEstimatedValue(), 0.01);
    }

    @Test
    public void deserializeFixedAssetCategoryFile() throws IOException {
        // @formatter:off
        List<FixedAssetCategoryEVM> categories =
            ExcelFileUtils
                .deserializeFixedAssetCategoryFile(toBytes(readFile("asset_category.xlsx")));
        // @formatter:on

        assertEquals(1000, categories.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", categories.get(0).getCategoryCode());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", categories.get(0).getCategoryName());
        assertEquals("Wrong Scanned document! : ", 555, categories.get(0).getDepreciationRegimeId());
    }

    @Test
    public void deserializeFixedAssetInvoiceFile() throws IOException {
        // @formatter:off
        List<FixedAssetInvoiceEVM> invoices =
            ExcelFileUtils
                .deserializeFixedAssetInvoiceFile(toBytes(readFile("asset_invoice.xlsx")));
        // @formatter:on

        assertEquals(1000, invoices.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", invoices.get(0).getDealerName());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", invoices.get(0).getInvoiceReference());
        assertEquals("Wrong Scanned document! : ", 555, invoices.get(0).getInvoiceAmount(), 0.01);
    }

    @Test
    public void deserializeFixedAssetItemFile() throws IOException {
        // @formatter:off
        List<FixedAssetItemEVM> assetItems =
            ExcelFileUtils
                .deserializeFixedAssetItemFile(toBytes(readFile("asset_item.xlsx")));
        // @formatter:on

        assertEquals(1000, assetItems.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", assetItems.get(0).getAssetCategory());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", assetItems.get(0).getFixedAssetDescription());
        assertEquals("Wrong Scanned document! : ", 555, assetItems.get(0).getFixedAssetSerialCode());
    }
}
