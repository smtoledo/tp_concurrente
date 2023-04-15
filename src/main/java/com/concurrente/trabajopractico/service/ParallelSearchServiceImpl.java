package com.concurrente.trabajopractico.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.concurrente.trabajopractico.model.FileResult;
import com.concurrente.trabajopractico.tasks.SearchTask;

@Service("parallelService")
public class ParallelSearchServiceImpl implements ParallelSearchService{

    @Value("${documents.path}")
    private String documentsPath;

    @Override
    public List<FileResult> searchInDocuments(String keyword) throws IOException {
        
        List<Path> filesInFolder = new ArrayList<>();

        try{
          filesInFolder = Files.walk(Paths.get(documentsPath))
                  .filter(Files::isRegularFile)
                  .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<FileResult> fileResults = new ArrayList<FileResult>();

        ForkJoinPool pool = ForkJoinPool.commonPool();  

        // System.out.println("Total number of active threads before invoking: " + pool.getActiveThreadCount());

        fileResults = pool.invoke(new SearchTask(filesInFolder, keyword));

        //iterar fileResults y sacar documentos con mas de 10 ocurrencias

        return fileResults;
    }

}