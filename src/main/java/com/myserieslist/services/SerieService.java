package com.myserieslist.services;

import com.myserieslist.dto.Pagination;
import com.myserieslist.dto.SerieRecord;

import java.io.IOException;

public interface SerieService {
    
    Long save(SerieRecord serieRecord) throws IOException;

    Long update(SerieRecord serieRecord);

    SerieRecord getById(Long id);

    Pagination<SerieRecord> paginated(
            Pagination<SerieRecord> pagination
    );

    void remove(Long id);
}
