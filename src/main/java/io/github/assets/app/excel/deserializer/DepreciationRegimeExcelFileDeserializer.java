package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import io.github.assets.app.excel.ExcelFileDeserializer;
import io.github.assets.app.model.DepreciationRegimeEVM;

import java.util.List;

public class DepreciationRegimeExcelFileDeserializer implements ExcelFileDeserializer<DepreciationRegimeEVM> {

    private final DefaultExcelFileDeserializer<DepreciationRegimeEVM> defaultExcelFileDeserializer;

    public DepreciationRegimeExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.defaultExcelFileDeserializer = new DefaultExcelFileDeserializer<DepreciationRegimeEVM>(DepreciationRegimeEVM.class,poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<DepreciationRegimeEVM> deserialize(final byte[] excelFile) {

        return defaultExcelFileDeserializer.deserialize(excelFile);
    }
}
