package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationUploadEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class AmortizationUploadExcelFileDeserializer extends BinaryExcelFileDeserializer<AmortizationUploadEVM> implements ExcelFileDeserializer<AmortizationUploadEVM> {

    public AmortizationUploadExcelFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }


    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<AmortizationUploadEVM> deserialize(final byte[] excelFile) {

        InputStream amortizationUploadStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<AmortizationUploadEVM> amortizationUploadItems = Poiji.fromExcel(amortizationUploadStream, PoijiExcelType.XLSX, AmortizationUploadEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} amortization upload items deserialized from file: {} in {} millis", amortizationUploadItems.size(), excelFile.toString(), readTime);

        return amortizationUploadItems;
    }
}
