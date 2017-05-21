package de.robinschuerer.bank;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.slf4j.Logger;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public abstract class AbstractAccountExportReader {

    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(AbstractAccountExportReader.class);


    public <T> List<T> loadObjectList(
        @Nonnull final Class<T> type,
        @Nonnull final File file) {

        try {
            CsvMapper mapper = new CsvMapper();
            final CsvSchema bootstrapSchema = mapper
                .schemaFor(type)
                .withHeader()
                .withQuoteChar('"')
                .withColumnSeparator(';');

            MappingIterator<T> readValues = mapper
                .readerFor(type)
                .with(bootstrapSchema)
                .readValues(new InputStreamReader(new FileInputStream(file), "ISO-8859-1"));

            return readValues.readAll();
        } catch (Exception e) {
            LOGGER.error("Error occurred while loading object list from file " + file.getName(), e);
            return Collections.emptyList();
        }
    }
}
