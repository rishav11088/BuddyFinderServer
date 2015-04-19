package in.ac.iiitd.buddyfinder.model.push;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

/**
 * Created by Rishav Jain on 19-04-2015.
 * GcmResponse
 */
public class GcmResponse {
    private int multicast_id;
    private int success;
    private int failure;
    private int canonical_ids;
    private HashMap<String, String> results;

    public GcmResponse() {
    }

    public GcmResponse(int multicast_id, int success, int failure, int canonical_ids, HashMap<String, String> results) {
        this.multicast_id = multicast_id;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
        this.results = results;
    }

    public int getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(int multicast_id) {
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public HashMap<String, String> getResults() {
        return results;
    }

    public void setResults(HashMap<String, String> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "<Object to JSON Error>";
        }
    }
}
