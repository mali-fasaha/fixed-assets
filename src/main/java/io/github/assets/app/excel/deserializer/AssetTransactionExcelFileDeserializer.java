package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import io.github.assets.app.excel.ExcelFileDeserializer;
import io.github.assets.app.model.AssetTransactionEVM;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

public class AssetTransactionExcelFileDeserializer implements ExcelFileDeserializer<AssetTransactionEVM> {

    private final DefaultExcelFileDeserializer<AssetTransactionEVM> defaultExcelFileDeserializer;

    public AssetTransactionExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.defaultExcelFileDeserializer =
            new DefaultExcelFileDeserializer<AssetTransactionEVM>(AssetTransactionEVM.class, poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<AssetTransactionEVM> deserialize(final byte[] excelFile) {

        return defaultExcelFileDeserializer.deserialize(excelFile);
    }
}
