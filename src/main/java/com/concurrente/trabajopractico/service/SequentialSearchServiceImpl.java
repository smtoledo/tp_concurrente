package com.concurrente.trabajopractico.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.concurrente.trabajopractico.model.FileResult;

@Service("sequentialService")
public class SequentialSearchServiceImpl implements SequentialSearchService{

    @Value("${documents.path}")
    private String documentsPath;

    @Override
    public List<FileResult> searchInDocuments(String keyword) throws IOException {
        
        List<File> filesInFolder = Files.walk(Paths.get(documentsPath))
                                .filter(Files::isRegularFile)
                                .map(Path::toFile)
                                .collect(Collectors.toList());

        List<FileResult> fileResultsList = new ArrayList<>();

        for (File file : filesInFolder){
            try{
                int counter = 0;

                StopWatch watch = new StopWatch();
                watch.start();

                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String line = "";

                while ((line = bufferedReader.readLine()) != null){
                    String[] words = line.split("[\\s\\p{Punct}]+");
                    String regex = "(?i)\\b" + keyword + "\\b"; 
                    for (String word : words){
                        if (word.matches(regex))
                            counter++;
                    }
                }
                bufferedReader.close();
                
                watch.stop();

                FileResult fileResult = new FileResult();
                fileResult.setDocumentName(file.getName());
                fileResult.setOcurrencies(counter);
                fileResult.setSearchTime(watch.getTotalTimeSeconds());
                fileResultsList.add(fileResult);
            } 
            catch(Exception e) {
              System.out.print("\n\t Error processing file : "+file.getName());
            }
        
        }

        return fileResultsList;
    }

}