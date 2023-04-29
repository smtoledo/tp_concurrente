package com.concurrente.trabajopractico.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

import org.springframework.util.StopWatch;

import com.concurrente.trabajopractico.model.FileResult;
import com.concurrente.trabajopractico.model.WorkerResult;

public class FileSearchTask extends RecursiveTask<List<WorkerResult>> {

  private static final long serialVersionUID = 1L;
  private final List<Path> partition;
  private final String keyword;

  public FileSearchTask(List<Path> partition, String keyword) {
    this.partition = partition;
    this.keyword = keyword;
  }

  @Override
  protected List<WorkerResult> compute() {

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

}
