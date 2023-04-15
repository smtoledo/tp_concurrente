package com.concurrente.trabajopractico.controller;
 
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.concurrente.trabajopractico.model.FileResult;
import com.concurrente.trabajopractico.service.*;

@RestController
public class SearchController {

    @Autowired
    SequentialSearchService secuencialService;
    
    @Autowired
    ParallelSearchService parallelService;

    @GetMapping("/find_sequencially/{keyword}")
	public List<FileResult> find_sequencially(@PathVariable("keyword") String keyword) {
		try {
            return secuencialService.searchInDocuments(keyword);
        } catch (IOException e) {
            return null;
        }
	}


    @GetMapping("/find_in_parallel/{keyword}")
	public List<FileResult> find_in_parallel(@PathVariable("keyword") String keyword) {
		try {
            return parallelService.searchInDocuments(keyword);
        } catch (IOException e) {
            return null;
        }
	}
}