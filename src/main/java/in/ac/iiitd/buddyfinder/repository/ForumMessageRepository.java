package in.ac.iiitd.buddyfinder.repository;

import in.ac.iiitd.buddyfinder.model.object.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Rishav Jain on 18-04-2015.
 */
public interface ForumMessageRepository extends MongoRepository<Forum, String> {
}
