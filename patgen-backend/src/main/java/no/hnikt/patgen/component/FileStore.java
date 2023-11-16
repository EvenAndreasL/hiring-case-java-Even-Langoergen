package no.hnikt.patgen.component;

import java.io.IOException;
import java.util.List;

public interface FileStore {

    void updateItem(String filename, String oldItem, String newItem) throws IOException;

    void deleteItem(String filename, String item) throws IOException;

    void writeItemIfNotExist(String filename, String item) throws IOException;

    void writeAllItems(String filename, List<String> allItems, boolean append) throws IOException;
	
    void writeAllItems(String filename, List<String> allItems) throws IOException;

	List<String> readAllItems(String filename) throws IOException;
}
