package io.github.assets.app.excel;

import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.prepayments.app.excel.deserializer.AmortizationEntryExcelFileDeserializer;
import io.github.prepayments.app.excel.deserializer.AmortizationUploadExcelFileDeserializer;
import io.github.prepayments.app.excel.deserializer.PrepaymentEntryExcelFileDeserializer;
import io.github.prepayments.app.excel.deserializer.RegisteredSupplierDataEntryFileDeserializer;
import io.github.prepayments.app.excel.deserializer.ServiceOutletDataEntryFileDeserializer;
import io.github.prepayments.app.excel.deserializer.TransactionAccountDataEntryExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controls the business logic for deserializing excel file data
 */
public class ExcelFileUtils {

    public static List<AmortizationEntryEVM> deserializeAmortizationFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<AmortizationEntryEVM> excelFileDeserializer = new AmortizationEntryExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<PrepaymentEntryEVM> deserializePrepaymentsFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<PrepaymentEntryEVM> excelFileDeserializer = new PrepaymentEntryExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<RegisteredSupplierEVM> deserializeSuppliersFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<RegisteredSupplierEVM> excelFileDeserializer = new RegisteredSupplierDataEntryFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<ServiceOutletEVM> deserializeServiceOutletFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<ServiceOutletEVM> excelFileDeserializer = new ServiceOutletDataEntryFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<TransactionAccountEVM> deserializeTransactionAccountFile(byte[] dataEntryFile) {
        ExcelFileDeserializer<TransactionAccountEVM> excelFileDeserializer = new TransactionAccountDataEntryExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<AmortizationUploadEVM> deserializeAmortizationUploadFile(final byte[] dataEntryFile) {
        ExcelFileDeserializer<AmortizationUploadEVM> excelFileDeserializer = new AmortizationUploadExcelFileDeserializer(
            PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
        return excelFileDeserializer.deserialize(dataEntryFile);
    }
}
