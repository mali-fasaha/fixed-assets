package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import io.github.assets.app.excel.ExcelFileDeserializer;
import io.github.assets.app.model.FixedAssetCategoryEVM;

import java.util.List;

public class FixedAssetCategoryExcelFileDeserializer implements ExcelFileDeserializer<FixedAssetCategoryEVM> {

    private final DefaultExcelFileDeserializer<FixedAssetCategoryEVM> deserializer;

    public FixedAssetCategoryExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.deserializer = new DefaultExcelFileDeserializer<FixedAssetCategoryEVM>(FixedAssetCategoryEVM.class, poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<FixedAssetCategoryEVM> deserialize(final byte[] excelFile) {
        return deserializer.deserialize(excelFile);
    }
}
