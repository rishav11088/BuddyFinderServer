package in.ac.iiitd.buddyfinder.repository;

import in.ac.iiitd.buddyfinder.model.client.UserServiceApi;
import in.ac.iiitd.buddyfinder.model.object.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by Rishav Jain on 18-04-2015.
 * UserRepository
 */
public interface UserRepository extends MongoRepository<User, String> {

    User findById(@Param(UserServiceApi.USER_ID_QUERY_PARAMETER) String id);
}
