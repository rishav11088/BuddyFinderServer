package in.ac.iiitd.buddyfinder.repository;

import in.ac.iiitd.buddyfinder.model.push.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Rishav Jain on 17-04-2015.
 * DeviceRepository
 */
public interface DeviceRepository extends MongoRepository<Device,String> {

}
