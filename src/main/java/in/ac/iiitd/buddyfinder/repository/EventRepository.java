package in.ac.iiitd.buddyfinder.repository;

import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Rishav Jain on 19-04-2015.
 */
public interface EventRepository extends MongoRepository<Event, String> {

    Event findById(@Param(EventServiceApi.EVENT_ID_QUERY_PARAMETER) String id);

    List<Event> findByCategory(@Param(EventServiceApi.EVENT_CATEGORY_QUERY_PARAMETER) String category);
}
