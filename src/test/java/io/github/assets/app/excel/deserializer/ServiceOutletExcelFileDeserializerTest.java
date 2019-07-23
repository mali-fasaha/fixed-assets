package io.github.assets.app.excel.deserializer;

import com.poiji.option.PoijiOptions.PoijiOptionsBuilder;
import io.github.assets.app.model.ServiceOutletEVM;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import static io.github.assets.app.excel.ExcelTestUtil.getDefaultPoijiOptions;
import static io.github.assets.app.excel.ExcelTestUtil.readFile;
import static io.github.assets.app.excel.ExcelTestUtil.toBytes;
import static org.junit.Assert.*;

@Slf4j
public class ServiceOutletExcelFileDeserializerTest {

    private ServiceOutletExcelFileDeserializer serviceOutletExcelFileDeserializer;

    @Before
    public void setUp() throws Exception {

        // @formatter:off
        serviceOutletExcelFileDeserializer =
            new ServiceOutletExcelFileDeserializer(getDefaultPoijiOptions());
         // @formatter:on
    }

    @Test
    public void deserialize() throws IOException {
        // @formatter:off
        List<ServiceOutletEVM> serviceOutlets =
            serviceOutletExcelFileDeserializer.deserialize(toBytes(readFile("service_outlets.xlsx")));
        // @formatter:on

        log.info("ServiceOutlet # 1: {}", serviceOutlets.get(0));
        log.info("ServiceOutlet # 1000: {}", serviceOutlets.get(999));

        assertEquals(1000, serviceOutlets.size());
        assertEquals("5 Springview Park", serviceOutlets.get(0).getLocation());
        assertEquals("Human Resources", serviceOutlets.get(0).getDescription());
        assertEquals("802", serviceOutlets.get(0).getServiceOutletCode());

        assertEquals("17315 Fieldstone Center", serviceOutlets.get(999).getLocation());
        assertEquals("Training", serviceOutlets.get(999).getDescription());
        assertEquals("693", serviceOutlets.get(999).getServiceOutletCode());
    }
}
