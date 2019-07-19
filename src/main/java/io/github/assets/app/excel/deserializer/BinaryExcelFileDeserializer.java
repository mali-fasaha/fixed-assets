package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions;
import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.prepayments.app.excel.ExcelFileDeserializer;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;

public abstract class BinaryExcelFileDeserializer<T> implements ExcelFileDeserializer<T> {

    final PoijiOptions poijiOptions;

    BinaryExcelFileDeserializer() {

        this.poijiOptions = PoijiOptionsBuilder.settings(1).ignoreHiddenSheets(true).preferNullOverDefault(true).datePattern("yyyy/MM/dd").dateTimeFormatter(DateTimeFormatter.ISO_DATE_TIME).build();
    }

    public BinaryExcelFileDeserializer(PoijiOptions poijiOptions) {

        this.poijiOptions = poijiOptions;
    }

    InputStream getFileInputStream(byte[] byteArray) {

        return new ByteArrayInputStream(byteArray);
    }
}
