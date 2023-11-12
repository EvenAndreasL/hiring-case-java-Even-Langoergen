package no.hnikt.patgen.component;

import java.io.IOException;
import java.util.List;

public interface FileStore {

	void writeAllItems(String filename, List<String> allItems) throws IOException;

	List<String> readAllItems(String filename) throws IOException;
}
