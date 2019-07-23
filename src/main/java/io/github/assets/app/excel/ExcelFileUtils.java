package io.github.assets.app.excel;

import com.poiji.option.PoijiOptions;
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

    public static PoijiOptions getDefaultPoijiOptions() {

        // @formatter:off
        return PoijiOptionsBuilder.settings()
                           .ignoreHiddenSheets(true)
                           .preferNullOverDefault(true)
                           .datePattern("yyyy/MM/dd")
                           .dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME)
                           .build();
        // @formatter:on
    }

    public static List<ServiceOutletEVM> deserializeAmortizationFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<ServiceOutletEVM> excelFileDeserializer = new ServiceOutletExcelFileDeserializer(getDefaultPoijiOptions());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<AssetTransactionEVM> deserializePrepaymentsFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<AssetTransactionEVM> excelFileDeserializer = new AssetTransactionExcelFileDeserializer(getDefaultPoijiOptions());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }
}
