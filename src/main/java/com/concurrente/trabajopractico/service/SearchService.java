package com.concurrente.trabajopractico.service;

import java.io.IOException;
import com.concurrente.trabajopractico.model.*;

public interface SearchService {

    Result searchInDocuments(String keyword) throws IOException;

}
