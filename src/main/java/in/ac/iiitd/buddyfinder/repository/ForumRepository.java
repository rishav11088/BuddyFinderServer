package in.ac.iiitd.buddyfinder.repository;

import in.ac.iiitd.buddyfinder.model.client.EventServiceApi;
import in.ac.iiitd.buddyfinder.model.object.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Rishav Jain on 18-04-2015.
 */
public interface ForumRepository extends MongoRepository<Forum, String> {

    Forum findById(@Param(EventServiceApi.EVENT_ID_QUERY_PARAMETER) String id);
}
