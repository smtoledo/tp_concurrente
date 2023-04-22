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
import org.springframework.util.StopWatch;

import com.concurrente.trabajopractico.model.*;
import com.concurrente.trabajopractico.tasks.SearchTask;

@Service("parallelService")
public class ParallelSearchServiceImpl implements ParallelSearchService {

    @Value("${documents.path}")
    private String documentsPath;

    @Override
    public Result searchInDocuments(String keyword) throws IOException {

        StopWatch main_watch = new StopWatch();
        main_watch.start();

        List<Path> filesInFolder = new ArrayList<>();

        try {
            filesInFolder = Files.walk(Paths.get(documentsPath))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Result result = new Result();

        List<WorkerResult> workerResultlist = new ArrayList<>();

        ForkJoinPool pool = ForkJoinPool.commonPool();

        // System.out.println("Total number of active threads before invoking: " +
        // pool.getActiveThreadCount());

        workerResultlist = pool.invoke(new SearchTask(filesInFolder, keyword));

        // iterar fileResults y sacar documentos con mas de 10 ocurrencias

        result.setWorkersResult(workerResultlist);
        
        main_watch.stop();
        result.setTotalSearchTime(main_watch.getTotalTimeSeconds());

        return result;
    }

}