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

public class SearchTask extends RecursiveTask<List<FileResult>> {
  //SUB TASK - SLAVE

  private static final long serialVersionUID = 1L;
  private final List<Path> filesPaths;
  private final String keyword;

  private static final int THRESHOLD = 8;
 

  public SearchTask(List<Path> filesPaths, String keyword) {
    this.filesPaths = filesPaths;
    this.keyword = keyword;
  }

  @Override
  protected List<FileResult> compute() {

    int numberOfProcessors = Runtime.getRuntime().availableProcessors();
    int numberOfFiles = this.filesPaths.size();

    if (numberOfFiles < THRESHOLD){
      return processFiles(this.filesPaths);
    }else{
      List<FileResult> fileResults = new ArrayList<FileResult>();

      List<SearchTask> tasks = createSubtasks(numberOfProcessors, numberOfFiles);
      for (SearchTask task : tasks) {
          task.fork(); //arranges to asynchronously execute this task in the pool the current task is running in
      }

      for (SearchTask task : tasks) {
          fileResults.addAll(task.join()); //returns the result of the computation when it is done
      }
      return fileResults;
    }

  }

  private List<FileResult> processFiles(List<Path> partition){
    
    List<FileResult> fileResultsList = new ArrayList<>();

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

      } catch (Exception e) {
        System.out.print("\n\t Error processing file : " + file.getName());
      }
    }

    return fileResultsList;
  }

  private List<SearchTask> createSubtasks(int numberOfProcessors, int numberOfFiles){

    List<SearchTask> subTasksList = new ArrayList<SearchTask>();  

    int partitionSize = (int) Math.ceil((double)numberOfFiles/numberOfProcessors);
    for (int i = 0; i < numberOfFiles; i += partitionSize){
        List<Path> partition = filesPaths.subList(i, Math.min(i+partitionSize, numberOfFiles));
        subTasksList.add(new SearchTask(partition, keyword));
    }

    return subTasksList;
  }
}
