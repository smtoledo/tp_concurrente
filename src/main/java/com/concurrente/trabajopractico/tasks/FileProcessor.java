package com.concurrente.trabajopractico.tasks;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

import org.springframework.util.StopWatch;

import com.concurrente.trabajopractico.model.FileResult;

public class FileProcessor extends RecursiveTask<List<FileResult>> {

  private static final long serialVersionUID = 1L;
  private final String path;
  private final String extension;
  private final String keyword;

  public FileProcessor(String path, String extension, String keyword) {
    this.path = path;
    this.extension = extension;
    this.keyword = keyword;
  }

  @Override
  protected List<FileResult> compute() {

    List<FileResult> fileResultsList = new ArrayList<>();

    List<File> filesInFolder;
    try {
      filesInFolder = Files.walk(Paths.get(path))
          .filter(Files::isRegularFile)
          .map(Path::toFile)
          .collect(Collectors.toList());

      for (File file : filesInFolder) {
        if (checkFile(file.getName())) {
          try {
            int counter = 0;

            StopWatch watch = new StopWatch();
            watch.start();

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
              // String[] words = line.split("\\s+"); //remover comas y puntos y signos
              String[] words = line.split("[\\s\\p{Punct}]+");
              String regex = "(?i)\\b" + keyword + "\\b";
              for (String word : words) {
                // if (word.equalsIgnoreCase(keyword))
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
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return fileResultsList;
  }

  private boolean checkFile(String name) {
    return name.endsWith(extension);
  }
}
