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
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static io.github.assets.app.excel.ExcelTestUtil.readFile;
import static io.github.assets.app.excel.ExcelTestUtil.toBytes;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExcelFileUtilsTest {

    @Test
    public void deserializeServiceOutletFile() throws IOException {

        // @formatter:off
        List<ServiceOutletEVM> serviceOutlets =
            ExcelFileUtils
                .deserializeServiceOutletFile(toBytes(readFile("service_outlets.xlsx")));
        // @formatter:on

        assertEquals(12, serviceOutlets.size());
        assertEquals("5 Springview Park", serviceOutlets.get(0).getLocation());
        assertEquals("Human Resources", serviceOutlets.get(0).getDescription());
        assertEquals("802", serviceOutlets.get(0).getServiceOutletCode());

        assertEquals("274 Caliangt Court", serviceOutlets.get(11).getLocation());
        assertEquals("Services", serviceOutlets.get(11).getDescription());
        assertEquals("885", serviceOutlets.get(11).getServiceOutletCode());
    }

    @Test
    public void deserializeAssetTransactionFile() throws IOException {

        // @formatter:off
        List<AssetTransactionEVM> transactions =
            ExcelFileUtils
                .deserializeAssetTransactionFile(toBytes(readFile("asset_transactions.xlsx")));
        // @formatter:on

        assertEquals("Incorrect number of transactions : ",997, transactions.size());
        assertEquals("Wrong transaction id! : ", "TRN 218", transactions.get(0).getTransactionReference());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", transactions.get(0).getTransactionDate());
        assertEquals("Wrong Scanned document! : ", 555, transactions.get(0).getScannedDocumentId());

        assertEquals("Incorrect transaction id! : ", "TRN 789", transactions.get(996).getTransactionReference());
        assertEquals("Incorrect Transaction date! : ", "2018/11/27", transactions.get(996).getTransactionDate());
        assertEquals("Incorrect Scanned document! : ", 534, transactions.get(996).getScannedDocumentId());
    }

    @Test
    public void deserializeDealerFile() throws IOException {
        // @formatter:off
        List<DealerEVM> dealers =
            ExcelFileUtils
                .deserializeDealerFile(toBytes(readFile("dealers.xlsx")));
        // @formatter:on

        assertEquals("Incorrect # of dealers:",80, dealers.size());

        assertEquals("Incorrect Title Types! : ", "Honorable", dealers.get(0).getTitleTypes());
        assertEquals("Incorrect Dealer's Name! : ", "Barclay Shires", dealers.get(0).getDealerName());
        assertEquals("Incorrect Dealer's Address! : ", "116 Superior Street", dealers.get(0).getDealerAddress());
        assertEquals("Incorrect Dealer's Phone #! : ", "20 Farmco Lane", dealers.get(0).getDealerPhoneNumber());
        assertEquals("Incorrect Dealer's Email #! : ", "bshires0@fc2.com", dealers.get(0).getDealerEmail());
        assertEquals("Incorrect Dealer's Bank Name #! : ", "Barton, Beatty and Parker", dealers.get(0).getBankName());
        assertEquals("Incorrect Bank Account #! : ", "3559275856982020", dealers.get(0).getBankAccountNumber());
        assertEquals("Incorrect Bank Branch #! : ", "Dwight", dealers.get(0).getBankBranch());
        assertEquals("Incorrect Bank Swift Code #! : ", "KW51 LMLF VI6K LKNU MOAW IIR4 AYCU CK", dealers.get(0).getBankSwiftCode());
        assertEquals("Incorrect Bank Physical Address #! : ", "98211 Golf Course Plaza", dealers.get(0).getBankPhysicalAddress());
        assertFalse("Incorrect Response for Domicile #! : ", dealers.get(0).isLocallyDomiciled());
        assertEquals("Incorrect Tax Authority Reference #! : ", "A0004783", dealers.get(0).getTaxAuthorityRef());

        assertEquals("Incorrect Title Types! : ", "Rev", dealers.get(79).getTitleTypes());
        assertEquals("Incorrect Dealer's Name! : ", "Alasteir Scriviner", dealers.get(79).getDealerName());
        assertEquals("Incorrect Dealer's Address! : ", "53 Tony Way", dealers.get(79).getDealerAddress());
        assertEquals("Incorrect Dealer's Phone #! : ", "778 Dryden Way", dealers.get(79).getDealerPhoneNumber());
        assertEquals("Incorrect Dealer's Email #! : ", "ascriviner27@google.de", dealers.get(79).getDealerEmail());
        assertEquals("Incorrect Dealer's Bank Name #! : ", "Stracke-Schuppe", dealers.get(79).getBankName());
        assertEquals("Incorrect Bank Account #! : ", "5434017219706290", dealers.get(79).getBankAccountNumber());
        assertEquals("Incorrect Bank Branch #! : ", "Novick", dealers.get(79).getBankBranch());
        assertEquals("Incorrect Bank Swift Code #! : ", "FR65 8656 2742 07DD GVO6 UAXU Q59", dealers.get(79).getBankSwiftCode());
        assertEquals("Incorrect Bank Physical Address #! : ", "37 Clove Lane", dealers.get(79).getBankPhysicalAddress());
        assertTrue("Incorrect Response for Domicile #! : ", dealers.get(79).isLocallyDomiciled());
        assertEquals("Incorrect Tax Authority Reference #! : ", "A0004935", dealers.get(79).getTaxAuthorityRef());
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

        assertEquals("Wrong Depreciation Regime! : ", "STRAIGHT_LINE", excelFileItems.get(4).getAssetDecayType());
        assertEquals("Wrong Depreciation Rate! : ", 0.03, excelFileItems.get(4).getDepreciationRate(), 0.001);
        assertEquals("Wrong Description! : ", "Straight line depreciation", excelFileItems.get(4).getDescription());
    }

    @Test
    public void deserializeFixedAssetAssessmentFile() throws IOException {
        // @formatter:off
        List<FixedAssetAssessmentEVM> assessments =
            ExcelFileUtils
                .deserializeFixedAssetAssessmentFile(toBytes(readFile("asset_assessment.xlsx")));
        // @formatter:on
        assertEquals(1000, assessments.size());

        assertEquals("Incorrect description! : ", "Puncture wound with foreign body of foot", assessments.get(0).getDescription());
        assertEquals("Incorrect asset condition! : ", "EXCELLENT", assessments.get(0).getAssetCondition());
        assertEquals("Incorrect assessment date! : ", "2019/01/13", assessments.get(0).getAssessmentDate());
        assertEquals("Incorrect assessment remarks! : ", "Personal history of other drug therapy", assessments.get(0).getAssessmentRemarks());
        assertEquals("Incorrect assessing staff! : ", "Albrecht Ellsworthe", assessments.get(0).getNameOfAssessingStaff());
        assertEquals("Incorrect assessing contractor! : ", "Quimm", assessments.get(0).getNameOfAssessmentContractor());
        assertEquals("Incorrect current service outlet! : ", "802", assessments.get(0).getCurrentServiceOutletCode());
        assertEquals("Incorrect current physical address! : ", "6361 Independence Avenue", assessments.get(0).getCurrentPhysicalAddress());
        assertEquals("Incorrect next assessment date! : ", "2019/03/08", assessments.get(0).getNextAssessmentDate());
        assertEquals("Incorrect name of user! : ", "Ruthe Denniss", assessments.get(0).getNameOfUser());
        assertEquals("Incorrect fixed asset item id! : ", 541, assessments.get(0).getFixedAssetItemId());
        assertEquals("Incorrect estimated value! : ", 50646.97, assessments.get(0).getEstimatedValue(), 0.01);
        assertEquals("Incorrect estimated # of useful months! : ", 1, assessments.get(0).getEstimatedUsefulMonths(), 0.0001);

        assertEquals("Incorrect description! : ", "Nondisp fx of neck of first metacarpal bone, unsp hand", assessments.get(999).getDescription());
        assertEquals("Incorrect asset condition! : ", "WRITE_OFF", assessments.get(999).getAssetCondition());
        assertEquals("Incorrect assessment date! : ", "2019/05/06", assessments.get(999).getAssessmentDate());
        assertEquals("Incorrect assessment remarks! : ", "Nondisp fx of hook pro of hamate bone, r wrs, 7thK", assessments.get(999).getAssessmentRemarks());
        assertEquals("Incorrect assessing staff! : ", "Dorthea Clavering", assessments.get(999).getNameOfAssessingStaff());
        assertEquals("Incorrect assessing contractor! : ", "Cogibox", assessments.get(999).getNameOfAssessmentContractor());
        assertEquals("Incorrect current service outlet! : ", "674", assessments.get(999).getCurrentServiceOutletCode());
        assertEquals("Incorrect current physical address! : ", "2536 Forest Run Parkway", assessments.get(999).getCurrentPhysicalAddress());
        assertEquals("Incorrect next assessment date! : ", "2018/08/29", assessments.get(999).getNextAssessmentDate());
        assertEquals("Incorrect name of user! : ", "Jordain Stanners", assessments.get(999).getNameOfUser());
        assertEquals("Incorrect fixed asset item id! : ", 454, assessments.get(999).getFixedAssetItemId());
        assertEquals("Incorrect estimated value! : ", 270896.73, assessments.get(999).getEstimatedValue(), 0.01);
        assertEquals("Incorrect estimated # of useful months! : ", 167, assessments.get(999).getEstimatedUsefulMonths(), 0.0001);

    }

    @Test
    public void deserializeFixedAssetCategoryFile() throws IOException {
        // @formatter:off
        List<FixedAssetCategoryEVM> categories =
            ExcelFileUtils
                .deserializeFixedAssetCategoryFile(toBytes(readFile("asset_category.xlsx")));
        // @formatter:on

        assertEquals("Incorrect # of categories", 7, categories.size());

        assertEquals("Incorrect category code: ", "001542635256", categories.get(0).getCategoryCode());
        assertEquals("Incorrect category name: ", "COMPUTERS", categories.get(0).getCategoryName());
        assertEquals("Incorrect category description: ", "computers", categories.get(0).getCategoryDescription());
        assertEquals("Incorrect depreciation regime id: ", 4, categories.get(0).getDepreciationRegimeId());

        assertEquals("Incorrect category code: ", "001542658137", categories.get(6).getCategoryCode());
        assertEquals("Incorrect category name: ", "FURNITURE AND FITTINGS", categories.get(6).getCategoryName());
        assertEquals("Incorrect category description: ", "furniture and fittings", categories.get(6).getCategoryDescription());
        assertEquals("Incorrect depreciation regime id: ", 3, categories.get(6).getDepreciationRegimeId());
    }

    @Test
    public void deserializeFixedAssetInvoiceFile() throws IOException {
        // @formatter:off
        List<FixedAssetInvoiceEVM> invoices =
            ExcelFileUtils
                .deserializeFixedAssetInvoiceFile(toBytes(readFile("asset_invoice.xlsx")));
        // @formatter:on

        assertEquals(1000, invoices.size());

        assertEquals("Incorrect invoice reference: ", "736", invoices.get(0).getInvoiceReference());
        assertEquals("Incorrect invoice date: ", "2019/03/22", invoices.get(0).getInvoiceDate());
        assertEquals("Incorrect invoice amount: ", 378755.88, invoices.get(0).getInvoiceAmount(),0.01);
        assertFalse("Incorrect Response on IsProforma: ", invoices.get(0).isProforma());
        assertFalse("Incorrect Response on IsCredit Note: ", invoices.get(0).isCreditNote());
        assertEquals("Incorrect Dealer Id: ", 75, invoices.get(0).getDealerId());
        assertEquals("Incorrect Dealer Name: ", "Sylas Benedyktowicz", invoices.get(0).getDealerName());

        assertEquals("Incorrect invoice reference: ", "240", invoices.get(999).getInvoiceReference());
        assertEquals("Incorrect invoice date: ", "2018/10/21", invoices.get(999).getInvoiceDate());
        assertEquals("Incorrect invoice amount: ", 75399.96, invoices.get(999).getInvoiceAmount(),0.01);
        assertTrue("Incorrect Response on IsProforma: ", invoices.get(999).isProforma());
        assertFalse("Incorrect Response on IsCredit Note: ", invoices.get(999).isCreditNote());
        assertEquals("Incorrect Dealer Id: ", 63, invoices.get(999).getDealerId());
        assertEquals("Incorrect Dealer Name: ", "Aron Reightley", invoices.get(999).getDealerName());
    }

    @Test
    public void deserializeFixedAssetItemFile() throws IOException {
        // @formatter:off
        List<FixedAssetItemEVM> assetItems =
            ExcelFileUtils
                .deserializeFixedAssetItemFile(toBytes(readFile("asset_item.xlsx")));
        // @formatter:on

        assertEquals(999, assetItems.size());

        assertEquals("Incorrect Service Outlet Code in row index : "+ assetItems.get(0).getRowIndex(), "331", assetItems.get(0).getServiceOutletCode());
        assertEquals("Incorrect Asset category code in row index : "+ assetItems.get(0).getRowIndex(), "001542638824", assetItems.get(0).getAssetCategoryCode());
        assertEquals("Incorrect Asset category in row index : "+ assetItems.get(0).getRowIndex(), "MOTOR VEHICLES", assetItems.get(0).getAssetCategory());
        assertEquals("Incorrect Asset serial in row index : "+ assetItems.get(0).getRowIndex(), "#2c7e86", assetItems.get(0).getFixedAssetSerialCode());
        assertEquals("Incorrect Asset description in row index : "+ assetItems.get(0).getRowIndex(), "motor vehicles", assetItems.get(0).getFixedAssetDescription());
        assertEquals("Incorrect Asset purchase date in row index : "+ assetItems.get(0).getRowIndex(), "2019/04/16", assetItems.get(0).getPurchaseDate());
        assertEquals("Incorrect Asset purchase cost in row index : "+ assetItems.get(0).getRowIndex(), 430553.1, assetItems.get(0).getPurchaseCost(), 0.01);
        assertEquals("Incorrect Asset purchase transaction id in row index : "+ assetItems.get(0).getRowIndex(), 952, assetItems.get(0).getPurchaseTransactionId());
        assertEquals("Incorrect Asset ownership document id in row index : "+ assetItems.get(0).getRowIndex(), 964, assetItems.get(0).getOwnershipDocumentId());
    }
}
