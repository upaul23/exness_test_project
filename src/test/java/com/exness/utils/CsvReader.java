package com.exness.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvReader {


    public static Map<String, String> getLinesAsHashMaps(String file, String separator){
        List<Object> items = new ArrayList<>();
        Path path = Paths.get( System.getProperty( "user.dir" ), file );
        try {
            items = Files.lines( path ).collect(Collectors.toList());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        Map <String, String> map = new HashMap<>();
        for (Object i:items
             ) {
            String[] keyValues = i.toString().split(separator);
            map.put(keyValues[0], keyValues[1]);
        }
        return map;
    }



}
