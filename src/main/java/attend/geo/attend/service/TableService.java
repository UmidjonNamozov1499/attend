package attend.geo.attend.service;

import org.springframework.http.HttpEntity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public interface TableService {
    HttpEntity<?> getUserTable() throws IOException;
}
