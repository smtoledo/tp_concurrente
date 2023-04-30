package com.concurrente.trabajopractico.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import org.springframework.util.StopWatch;

import com.concurrente.trabajopractico.model.*;

public class SearchTask extends RecursiveTask<List<WorkerResult>> {

  private static final long serialVersionUID = 1L;
  private final List<Path> filesPaths;
  private final String keyword;
  private static final int THRESHOLD = 100;

  public SearchTask(List<Path> filesPaths, String keyword) {
    this.filesPaths = filesPaths;
    this.keyword = keyword;
  }

  @Override
  protected List<WorkerResult> compute() {

    int numberOfProcessors = Runtime.getRuntime().availableProcessors();
    int numberOfFiles = this.filesPaths.size();

    if (numberOfFiles < THRESHOLD) {
      System.out.println("available processors: "+numberOfProcessors+" / number of files: "+numberOfFiles);
      return processFiles(this.filesPaths);
    } else {
      List<WorkerResult> workersResults = new ArrayList<WorkerResult>();

      List<SearchTask> tasks = createSubtasks(numberOfProcessors, numberOfFiles);
      for (SearchTask task : tasks) {
        task.fork();
      }

      for (SearchTask task : tasks) {
        workersResults.addAll(task.join());
      }
      return workersResults;
    }

  }

  private List<WorkerResult> processFiles(List<Path> partition) {

    WorkerResult workerResult = new WorkerResult();
    workerResult.setWorkerId(Thread.currentThread().getName());

    StopWatch worker_watch = new StopWatch();
    worker_watch.start();

    for (Path path : partition) {

      File file = path.toFile();

      try {
        int counter = 0;

        StopWatch watch = new StopWatch();
        watch.start();

        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
          String[] words = line.split("[\\s\\p{Punct}]+");
          String regex = "(?i)\\b" + keyword + "\\b";
          for (String word : words) {
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

        workerResult.addFilesSearch(fileResult);

      } catch (Exception e) {
        System.out.print("\n\t Error processing file : " + file.getName());
      }
    }

    worker_watch.stop();
    workerResult.setSearchTime(worker_watch.getTotalTimeSeconds());

    List<WorkerResult> workerlist = new ArrayList<>();
    workerlist.add(workerResult);

    return workerlist;
  }

  private List<SearchTask> createSubtasks(int numberOfProcessors, int numberOfFiles) {

    List<SearchTask> subTasksList = new ArrayList<SearchTask>();

    int partitionSize = (int) Math.ceil((double) numberOfFiles / numberOfProcessors);
    for (int i = 0; i < numberOfFiles; i += partitionSize) {
      List<Path> partition = filesPaths.subList(i, Math.min(i + partitionSize, numberOfFiles));
      subTasksList.add(new SearchTask(partition, keyword));
    }

    return subTasksList;
  }
}
