package com.concurrente.trabajopractico.service;

import java.io.IOException;
import java.util.List;

import com.concurrente.trabajopractico.model.FileResult;

public interface SearchService {

    List<FileResult> searchInDocuments(String keyword) throws IOException;
}
