package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.ServiceOutletEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class ServiceOutletDataEntryFileDeserializer extends BinaryExcelFileDeserializer<ServiceOutletEVM> implements ExcelFileDeserializer<ServiceOutletEVM> {

    public ServiceOutletDataEntryFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<ServiceOutletEVM> deserialize(final byte[] excelFile) {

        InputStream solStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<ServiceOutletEVM> sols = Poiji.fromExcel(solStream, PoijiExcelType.XLSX, ServiceOutletEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} prepayment entries deserialized from file: {} in {} millis", sols.size(), excelFile.toString(), readTime);

        return sols;
    }
}
