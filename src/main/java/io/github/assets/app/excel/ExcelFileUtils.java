package io.github.assets.app.excel;

import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.assets.app.excel.deserializer.AssetTransactionExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.ServiceOutletExcelFileDeserializer;
import io.github.assets.app.model.AssetTransactionEVM;
import io.github.assets.app.model.ServiceOutletEVM;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controls the business logic for deserializing excel file data
 */
public class ExcelFileUtils {

    public static List<ServiceOutletEVM> deserializeAmortizationFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<ServiceOutletEVM> excelFileDeserializer = new ServiceOutletExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<AssetTransactionEVM> deserializePrepaymentsFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<AssetTransactionEVM> excelFileDeserializer = new AssetTransactionExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }
}
