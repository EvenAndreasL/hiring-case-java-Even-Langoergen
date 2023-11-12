package no.hnikt.patgen.component;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Wesley Snipes
 */

@Component
public class SimpleFileStore implements FileStore {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleFileStore.class);

    @Value("${dirname}")
    private String dirname;

    public void writeAllItems(String filename, List<String> allItems) throws IOException {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Paths.get(this.dirname, filename).toString()), StandardCharsets.UTF_8));
            for (String item : allItems) {
                writer.write(item);
                writer.newLine();
            }
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // well that was weird...
                LOG.warn("Could not close file", e);
            }
        }
    }

    public List<String> readAllItems(String filename) throws IOException {
        List<String> items = new ArrayList<>();
        BufferedReader reader = null;
        try {
            InputStream fileStream = new FileInputStream(Paths.get(this.dirname, filename).toString());
            reader = new BufferedReader(new InputStreamReader(fileStream, StandardCharsets.UTF_8));
            String item;
            while ((item = reader.readLine()) != null) {
                items.add(item);
            }
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                // well that was weird...
                LOG.warn("Could not close file", e);
            }
        }
        return items;
    }
}