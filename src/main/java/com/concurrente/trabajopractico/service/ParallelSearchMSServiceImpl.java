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
import com.concurrente.trabajopractico.tasks.FileSearchTask;

@Service("parallelMSService")
public class ParallelSearchMSServiceImpl implements ParallelSearchMSService {

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

    int numberOfProcessors = Runtime.getRuntime().availableProcessors()-1;
    int numberOfFiles = filesInFolder.size();

    Result result = new Result();

    ForkJoinPool pool = ForkJoinPool.commonPool();

    List<WorkerResult> workersResults = new ArrayList<WorkerResult>();

    List<FileSearchTask> tasks = createSubtasks(filesInFolder, keyword, numberOfProcessors, numberOfFiles);

    for (FileSearchTask task : tasks) {
      pool.execute(task); // Arranges for (asynchronous) execution of the given task.
    }

    for (FileSearchTask task : tasks) {
      workersResults.addAll(task.join()); // returns the result of the computation when it is done
    }

    result.setWorkersResult(workersResults);

    main_watch.stop();
    result.setTotalSearchTime(main_watch.getTotalTimeSeconds());

    return result;
  }

  private List<FileSearchTask> createSubtasks(List<Path> filesPaths, String keyword, int numberOfProcessors,
      int numberOfFiles) {

    List<FileSearchTask> subTasksList = new ArrayList<FileSearchTask>();

    int partitionSize = (int) Math.ceil((double) numberOfFiles / numberOfProcessors);
    for (int i = 0; i < numberOfFiles; i += partitionSize) {
      List<Path> partition = filesPaths.subList(i, Math.min(i + partitionSize, numberOfFiles));
      subTasksList.add(new FileSearchTask(partition, keyword));
    }

    return subTasksList;
  }

}