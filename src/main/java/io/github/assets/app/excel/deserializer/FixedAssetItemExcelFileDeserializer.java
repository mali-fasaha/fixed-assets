package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import io.github.assets.app.excel.ExcelFileDeserializer;
import io.github.assets.app.model.FixedAssetItemEVM;

import java.util.List;

public class FixedAssetItemExcelFileDeserializer implements ExcelFileDeserializer<FixedAssetItemEVM> {

    private final DefaultExcelFileDeserializer<FixedAssetItemEVM> deserializer;

    public FixedAssetItemExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.deserializer = new DefaultExcelFileDeserializer<FixedAssetItemEVM>(FixedAssetItemEVM.class, poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<FixedAssetItemEVM> deserialize(final byte[] excelFile) {
        return deserializer.deserialize(excelFile);
    }
}
