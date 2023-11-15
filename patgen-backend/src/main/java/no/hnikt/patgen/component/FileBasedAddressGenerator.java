package no.hnikt.patgen.component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

/*
 * @author Even Langørgen
 */

@Component
public class FileBasedAddressGenerator implements AddressGenerator {
    
    @Value("${dirname}")
    private String dirname;

    private final Random random = new Random(System.currentTimeMillis());

    public String streetNameAndNumber() {
        String address = "";
        try {
            address = selectAddress("streetname.txt");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return address + " " + (random.nextInt(99) + 1);
    }

    private String selectAddress(String filename) throws IOException {
		List<String> nameList = new ArrayList<>();

		InputStream fileStream ;
		if (dirname == null || "".equals(dirname)) {
			fileStream = new ClassPathResource(filename).getInputStream();
		} else {
			fileStream = new FileInputStream(Paths.get(dirname, filename).toString());
		}
		BufferedReader reader =  new BufferedReader(new InputStreamReader(fileStream, StandardCharsets.UTF_8));
		
		String currentName;
		while ((currentName = reader.readLine()) != null) {
			nameList.add(currentName);
		}
		
		return nameList.get(random.nextInt(nameList.size()));
	}
}
