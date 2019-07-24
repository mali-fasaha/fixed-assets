package io.github.assets.app.excel.deserializer;

import com.poiji.bind.Poiji;
import com.poiji.exception.PoijiExcelType;
import com.poiji.option.PoijiOptions;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.List;

import static io.github.assets.app.excel.deserializer.DeserializationUtils.getFileInputStream;

@Slf4j
public class DefaultExcelFileDeserializer<T> {

    private final Class<T> deserializationClass;
    private final PoijiOptions poijiOptions;

    DefaultExcelFileDeserializer(final Class<T> deserializationClass, final PoijiOptions poijiOptions) {
        this.deserializationClass = deserializationClass;
        this.poijiOptions = poijiOptions;
    }

    /*public DefaultExcelFileDeserializer(final Class<T> deserializationClass) {
        this(deserializationClass, PoijiOptionsBuilder.settings().ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build());
    }*/

    /*public DefaultExcelFileDeserializer(final PoijiOptions poijiOptions) {
        this.deserializationClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        this.poijiOptions = poijiOptions;
    }*/

    /**
     * This method opens a byte stream and converts the data file into a list of data items contained in its rows
     *
     * @param excelFile Received containing the data to be deserialized
     * @return List of data of type T
     */
    public List<T> deserialize(final byte[] excelFile) {
        InputStream fileInputStream = getFileInputStream(excelFile);
        long time = System.currentTimeMillis();
        List<T> entries = Poiji.fromExcel(fileInputStream, PoijiExcelType.XLSX, deserializationClass, poijiOptions);
        long readTime = System.currentTimeMillis() - time;
        log.info("{} entries deserialized from file: in {} millis", entries.size(), readTime);
        return entries;
    }
}