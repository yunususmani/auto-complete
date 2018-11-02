package com.pramati.autocomplete.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pramati.autocomplete.constants.Constants;

public class DataLoader {

	private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

	public static List<String> cities;

	static {
		BufferedReader bufferedReader = null;

		try {
			
			logger.info("loading data..........");
			ClassLoader classLoader = new DataLoader().getClass().getClassLoader();
			
			URI uri = classLoader.getResource("All_India_pincode_data_26022018.csv").toURI();

			bufferedReader = Files.newBufferedReader(Paths.get(uri), Charset.forName(Constants.CHAR_ENCODING));
			cities = bufferedReader.lines().skip(1).map((line) -> {
				String[] lines = line.split(",");
				return lines[7].toUpperCase();
			}).distinct().collect(Collectors.toList());
			logger.info("data loaded successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error in loading data from file: " + e.toString());
			throw new RuntimeException("Error in loading data from input file");
		} finally {
			try {
				if(bufferedReader != null)
					bufferedReader.close();
			} catch (IOException e) {
				logger.error("error in closing bufferedReader: " + e.toString());
			}
		}
	}

	public static List<String> getCities() {
		return cities;
	}

	public static void main(String[] args) {
		DataLoader.cities.forEach(city -> System.out.println(city));
	}
}
