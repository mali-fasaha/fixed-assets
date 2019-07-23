package io.github.assets.app.excel;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.assets.app.excel.deserializer.AssetTransactionExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.DealerExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.DepreciationRegimeExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.FixedAssetAssessmentExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.FixedAssetCategoryExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.FixedAssetInvoiceExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.FixedAssetItemExcelFileDeserializer;
import io.github.assets.app.excel.deserializer.ServiceOutletExcelFileDeserializer;
import io.github.assets.app.model.AssetTransactionEVM;
import io.github.assets.app.model.DealerEVM;
import io.github.assets.app.model.DepreciationRegimeEVM;
import io.github.assets.app.model.FixedAssetAssessmentEVM;
import io.github.assets.app.model.FixedAssetCategoryEVM;
import io.github.assets.app.model.FixedAssetInvoiceEVM;
import io.github.assets.app.model.FixedAssetItemEVM;
import io.github.assets.app.model.ServiceOutletEVM;
import io.github.assets.domain.DepreciationRegime;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controls the business logic for deserializing excel file data and initialization of file
 * deserializers, which is done manually without the help of the spring DI container.
 */
public class ExcelFileUtils {

    // Configure and use singleton PoijiOptions
    private static final ExcelFileDeserializer<ServiceOutletEVM> serviceOutletxcelFileDeserializer = new ServiceOutletExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<AssetTransactionEVM> assetTransactionExcelFileDeserializer = new AssetTransactionExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<DealerEVM> dealerExcelFileDeserializer = new DealerExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<DepreciationRegimeEVM> depreciationReimeExcelFileDeserializer = new DepreciationRegimeExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<FixedAssetAssessmentEVM> fixedAssetAssessmentExcelFileDeserializer = new FixedAssetAssessmentExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<FixedAssetCategoryEVM> fixedAssetCategoryExcelFileDeserializer = new FixedAssetCategoryExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<FixedAssetInvoiceEVM> fixedAssetInvoiceExcelFileDeserializer = new FixedAssetInvoiceExcelFileDeserializer(getDefaultPoijiOptions());
    private static final ExcelFileDeserializer<FixedAssetItemEVM> fixedAssetItemExcelFileDeserializer = new FixedAssetItemExcelFileDeserializer(getDefaultPoijiOptions());


    public static PoijiOptions getDefaultPoijiOptions() {
        return PoijiOptionsHolder.getInstance();
    }

    public static List<ServiceOutletEVM> deserializeServiceOutletFile(byte[] dataEntryFile) {
        return serviceOutletxcelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<AssetTransactionEVM> deserializeAssetTransactionFile(byte[] dataEntryFile) {
        return assetTransactionExcelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<DealerEVM> deserializeDealerFile(byte[] dataEntryFile) {
        return dealerExcelFileDeserializer.deserialize(dataEntryFile);
    }

    public static List<DepreciationRegimeEVM> deserializeDepreciationRegimeFile(byte[] dataEntry) {
        return depreciationReimeExcelFileDeserializer.deserialize(dataEntry);
    }

    public static List<FixedAssetAssessmentEVM> deserializeFixedAssetAssessmentFile(byte[] dataEntry) {
        return fixedAssetAssessmentExcelFileDeserializer.deserialize(dataEntry);
    }

    public static List<FixedAssetCategoryEVM> deserializeFixedAssetCategoryFile(byte[] dataEntry) {
        return fixedAssetCategoryExcelFileDeserializer.deserialize(dataEntry);
    }

    public static List<FixedAssetInvoiceEVM> deserializeFixedAssetInvoiceFile(byte[] dataEntry) {
        return fixedAssetInvoiceExcelFileDeserializer.deserialize(dataEntry);
    }

    public static List<FixedAssetItemEVM> fixedAssetItemFile(byte[] dataEntry) {
        return fixedAssetItemExcelFileDeserializer.deserialize(dataEntry);
    }

    /**
     * PoijiOptions is a configurations object. As it may appear to bewildered eye that the PoijiOptionsHolder
     * is an instance of over-engineering, but I would sooner ensure that I only have one PoijiOptions in the
     * entire applications because engineering for changes in this configuration would be too hard.
     * It is better to have an on demand initialization singleton that is also a double-locking checked
     * PoijiOptions as this object is used from reactive components running on Kafka generating messaging
     * events at any time during the application run, so there...
     */
    private static class PoijiOptionsHolder {
        private static volatile PoijiOptions INSTANCE;
        private static PoijiOptions getInstance() {
            if (INSTANCE == null) {
                synchronized (PoijiOptionsHolder.class) {
                    if (INSTANCE == null) {
                        // @formatter:off
                        INSTANCE = PoijiOptionsBuilder.settings()
                                                         .ignoreHiddenSheets(true)
                                                         .preferNullOverDefault(true)
                                                         .datePattern("yyyy/MM/dd")
                                                         .dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME)
                                                         .build();
                        // @formatter:on
                    }
                }
            }
            return INSTANCE;
        }
    }
}
