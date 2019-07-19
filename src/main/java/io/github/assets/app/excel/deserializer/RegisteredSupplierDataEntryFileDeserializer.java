package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import io.github.prepayments.app.excel.ExcelFileDeserializer;
import io.github.prepayments.app.messaging.filing.vm.RegisteredSupplierEVM;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

@Slf4j
public class RegisteredSupplierDataEntryFileDeserializer extends BinaryExcelFileDeserializer<RegisteredSupplierEVM> implements ExcelFileDeserializer<RegisteredSupplierEVM> {

    public RegisteredSupplierDataEntryFileDeserializer(final PoijiOptions poijiOptions) {
        super(poijiOptions);
    }

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    @Override
    public List<RegisteredSupplierEVM> deserialize(final byte[] excelFile) {
        InputStream supplierStream = getFileInputStream(excelFile);

        long time = System.currentTimeMillis();

        List<RegisteredSupplierEVM> suppliers = Poiji.fromExcel(supplierStream, PoijiExcelType.XLSX, RegisteredSupplierEVM.class, poijiOptions);

        long readTime = System.currentTimeMillis() - time;

        log.info("{} registered supplier entries deserialized from file: {} in {} millis", suppliers.size(), excelFile.toString(), readTime);

        return suppliers;
    }
}
