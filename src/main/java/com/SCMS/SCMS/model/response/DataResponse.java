package com.SCMS.SCMS.model.response;

import java.util.HashMap;
import java.util.Map;

public class DataResponse {
    public static Map<String, Object> success(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", message);
        response.put("data", data);
        return response;
    }

    public static Map <String, Object> success(Object data) {
        return success(data, "Success");
    }

    public static Map<String, Object> failed(Object data, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", message);

        return response;
    }

    public static Map<String, Object> failed() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        return response;
    }

}
