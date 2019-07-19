package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.AmortizationEntryEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

/**
 * This class is created to work with XLSX files
 */
@Slf4j
public class AmortizationEntryExcelFileDeserializer extends BinaryExcelFileDeserializer<AmortizationEntryEVM> implements ExcelFileDeserializer<AmortizationEntryEVM> {

    public AmortizationEntryExcelFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<AmortizationEntryEVM> deserialize(byte[] excelFile) {

        InputStream amortizationStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<AmortizationEntryEVM> amortizationEntries = Poiji.fromExcel(amortizationStream, PoijiExcelType.XLSX, AmortizationEntryEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} amortization entries deserialized from file: {} in {} millis", amortizationEntries.size(), excelFile.toString(), readTime);

        return amortizationEntries;
    }
}
