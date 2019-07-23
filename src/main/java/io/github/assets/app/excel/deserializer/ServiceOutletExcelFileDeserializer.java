package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import io.github.assets.app.excel.ExcelFileDeserializer;
import io.github.assets.app.model.ServiceOutletEVM;

import java.util.List;

public class ServiceOutletExcelFileDeserializer implements ExcelFileDeserializer<ServiceOutletEVM> {

    private final DefaultExcelFileDeserializer<ServiceOutletEVM> defaultExcelFileDesrializer;

    public ServiceOutletExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.defaultExcelFileDesrializer =
            new DefaultExcelFileDeserializer<ServiceOutletEVM>(ServiceOutletEVM.class, poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<ServiceOutletEVM> deserialize(final byte[] excelFile) {

        return defaultExcelFileDesrializer.deserialize(excelFile);
    }
}
