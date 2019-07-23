package io.github.assets.app.excel;

import io.github.assets.app.model.AssetTransactionEVM;
import io.github.assets.app.model.DepreciationRegimeEVM;
import io.github.assets.app.model.ServiceOutletEVM;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static io.github.assets.app.excel.ExcelTestUtil.readFile;
import static io.github.assets.app.excel.ExcelTestUtil.toBytes;
import static org.junit.Assert.*;

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
        log.info("ServiceOutlet # 1000: {}", serviceOutlets.get(999));

        assertEquals(1000, serviceOutlets.size());
        assertEquals("5 Springview Park", serviceOutlets.get(0).getLocation());
        assertEquals("Human Resources", serviceOutlets.get(0).getDescription());
        assertEquals("802", serviceOutlets.get(0).getServiceOutletCode());

        assertEquals("17315 Fieldstone Center", serviceOutlets.get(999).getLocation());
        assertEquals("Training", serviceOutlets.get(999).getDescription());
        assertEquals("693", serviceOutlets.get(999).getServiceOutletCode());
    }

    @Test
    public void deserializeAssetTransactionFile() throws IOException {

        // @formatter:off
        List<AssetTransactionEVM> transactions =
            ExcelFileUtils.deserializeAssetTransactionFile(toBytes(readFile("asset_transactions.xlsx")));
        // @formatter:on

        assertEquals(1000, transactions.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", transactions.get(0).getTransactionReference());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", transactions.get(0).getTransactionDate());
        assertEquals("Wrong Scanned document! : ", 555, transactions.get(0).getScannedDocumentId());
    }

    @Test
    public void deserializeDealerFile() {
    }

    @Test
    public void deserializeDepreciationRegimeFile() throws IOException {

        // @formatter:off
        List<DepreciationRegimeEVM> excelFileItems =
            ExcelFileUtils
                .deserializeDepreciationRegimeFile(toBytes(readFile("depreciation_regime.xlsx")));
        // @formatter:on

        assertEquals("Wron # of records! : ",5, excelFileItems.size());
        assertEquals("Wrong Depreciation Regime! : ", "STRAIGHT_LINE", excelFileItems.get(0).getAssetDecayType());
        assertEquals("Wrong Depreciation Rate! : ", 0.03, excelFileItems.get(0).getDepreciationRate(), 0.001);
        assertEquals("Wrong Description! : ", "Straight line depreciation", excelFileItems.get(0).getDescription());
    }

    @Test
    public void deserializeFixedAssetAssessmentFile() {
    }

    @Test
    public void deserializeFixedAssetCategoryFile() {
    }

    @Test
    public void deserializeFixedAssetInvoiceFile() {
    }

    @Test
    public void fixedAssetItemFile() {
    }
}
