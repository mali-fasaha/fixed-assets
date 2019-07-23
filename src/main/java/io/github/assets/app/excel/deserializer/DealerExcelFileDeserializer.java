package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import io.github.assets.app.excel.ExcelFileDeserializer;
import io.github.assets.app.model.DealerEVM;

import java.util.List;

public class DealerExcelFileDeserializer implements ExcelFileDeserializer<DealerEVM> {

    private final DefaultExcelFileDeserializer<DealerEVM> deserializer;

    public DealerExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.deserializer = new DefaultExcelFileDeserializer<DealerEVM>(DealerEVM.class, poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<DealerEVM> deserialize(final byte[] excelFile) {
        return deserializer.deserialize(excelFile);
    }
}
