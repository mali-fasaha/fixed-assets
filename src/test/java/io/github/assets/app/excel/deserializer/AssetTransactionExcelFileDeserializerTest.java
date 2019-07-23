package io.github.assets.app.excel.deserializer;

import io.github.assets.app.model.AssetTransactionEVM;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static io.github.assets.app.excel.ExcelTestUtil.getDefaultPoijiOptions;
import static io.github.assets.app.excel.ExcelTestUtil.readFile;
import static io.github.assets.app.excel.ExcelTestUtil.toBytes;
import static org.junit.Assert.*;

public class AssetTransactionExcelFileDeserializerTest {

    private AssetTransactionExcelFileDeserializer assetTransactionExcelFileDeserializer;

    @Before
    public void setUp() throws Exception {

        assetTransactionExcelFileDeserializer =
            new AssetTransactionExcelFileDeserializer(getDefaultPoijiOptions());
    }

    @Test
    public void deserialize() throws IOException {

        // @formatter:off
        List<AssetTransactionEVM> transactions =
            assetTransactionExcelFileDeserializer
                .deserialize(toBytes(readFile("asset_transactions.xlsx")));
        // @formatter:on

        assertEquals(1000, transactions.size());
        assertEquals("Wrong transaction id! : ","TRN 218", transactions.get(0).getTransactionReference());
        assertEquals("Wrong Transaction date! : ", "2018/08/16", transactions.get(0).getTransactionDate());
        assertEquals("Wrong Scanned document! : ", 555, transactions.get(0).getScannedDocumentId());
    }
}
