package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.PrepaymentEntryEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class PrepaymentEntryExcelFileDeserializer extends BinaryExcelFileDeserializer<PrepaymentEntryEVM> implements ExcelFileDeserializer<PrepaymentEntryEVM> {

    public PrepaymentEntryExcelFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<PrepaymentEntryEVM> deserialize(final byte[] excelFile) {

        InputStream prepaymentStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<PrepaymentEntryEVM> prepayments = Poiji.fromExcel(prepaymentStream, PoijiExcelType.XLSX, PrepaymentEntryEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} prepayment entries deserialized from file: {} in {} millis", prepayments.size(), excelFile.toString(), readTime);

        return prepayments;
    }
}
