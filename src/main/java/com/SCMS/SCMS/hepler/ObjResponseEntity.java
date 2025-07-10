package com.SCMS.SCMS.hepler;



import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
public class ObjResponseEntity <T>{
    @JsonProperty("timestamp")
    private long timestamp;
    @JsonProperty ("success")
    private boolean success;
    @JsonProperty ("message")
    private String message;
    @JsonProperty ("code")
    private int code;
    @JsonProperty ("data")
    private T data;
    public ObjResponseEntity<String> setSuccess(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setSuccess'");
    }
    public ObjResponseEntity<String> setError(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setError'");
    }
}
