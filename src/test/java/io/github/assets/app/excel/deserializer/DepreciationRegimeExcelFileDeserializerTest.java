package io.github.assets.app.excel.deserializer;

import io.github.assets.app.model.DepreciationRegimeEVM;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static io.github.assets.app.excel.ExcelTestUtil.getDefaultPoijiOptions;
import static io.github.assets.app.excel.ExcelTestUtil.readFile;
import static io.github.assets.app.excel.ExcelTestUtil.toBytes;
import static org.junit.Assert.*;

public class DepreciationRegimeExcelFileDeserializerTest {

    private DepreciationRegimeExcelFileDeserializer deserializer;

    @Before
    public void setUp() throws Exception {

        deserializer = new DepreciationRegimeExcelFileDeserializer(getDefaultPoijiOptions());
    }

    @Test
    public void deserialize() throws Exception {

        // @formatter:off
        List<DepreciationRegimeEVM> excelFileItems =
            deserializer
                .deserialize(toBytes(readFile("depreciation_regime.xlsx")));
        // @formatter:on

        assertEquals("Wron # of records! : ",5, excelFileItems.size());
        assertEquals("Wrong Depreciation Regime! : ", "STRAIGHT_LINE", excelFileItems.get(0).getAssetDecayType());
        assertEquals("Wrong Depreciation Rate! : ", 0.03, excelFileItems.get(0).getDepreciationRate(), 0.001);
        assertEquals("Wrong Description! : ", "Straight line depreciation", excelFileItems.get(0).getDescription());
    }
}
