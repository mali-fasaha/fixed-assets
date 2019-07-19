package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.TransactionAccountEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class TransactionAccountDataEntryExcelFileDeserializer extends BinaryExcelFileDeserializer<TransactionAccountEVM> implements ExcelFileDeserializer<TransactionAccountEVM> {

    public TransactionAccountDataEntryExcelFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<TransactionAccountEVM> deserialize(final byte[] excelFile) {
        InputStream transactionAccountStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<TransactionAccountEVM> transactionAccounts = Poiji.fromExcel(transactionAccountStream, PoijiExcelType.XLSX, TransactionAccountEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} transaction account entries deserialized from file: {} in {} millis", transactionAccounts.size(), excelFile.toString(), readTime);

        return transactionAccounts;
    }
}
