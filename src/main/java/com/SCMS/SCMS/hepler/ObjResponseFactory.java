package com.SCMS.SCMS.hepler;

import java.io.ObjectInputFilter.Status;
import java.sql.Timestamp;

public class ObjResponseFactory {
//     private static long getTimestamp(){
//         Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//         return timestamp.getTime();
//     }

//     public static <T> ObjResponseEntity<T> build() {
//         return new ObjResponseEntity<>();
//     }

//     public static <T> ObjResponseEntity<T> build(T data) {
//         ObjResponseEntity<T> ResponseEntity = build();
//         try {
//             if (data != null) {
//                 ResponseEntity.setTimestamp(getTimestamp());
//                 ResponseEntity.setSuccess(true);
//                 ResponseEntity.setMessage(MessageEnum.SUCCESS.getText());
//                 ResponseEntity.setCode(StatusMessageEnum.OK.getStatusCode());
//                 ResponseEntity.setData(data);
//             } else {
//                 ResponseEntity.setTimestamp(getTimestamp());
//                 ResponseEntity.setSuccess(false);
//                 ResponseEntity.setMessage(MessageEnum.NO_DATA.getText());
//                 ResponseEntity.setCode(StatusMessageEnum.NO_CONTENT.getStatusCode());
//                 ResponseEntity.setData(null);
//             }

//         } catch (Exception e) {
//             ResponseEntity.setTimestamp(getTimestamp());
//             ResponseEntity.setSuccess(false);
//             ResponseEntity.setMessage(MessageEnum.INTERNAL_SERVER_ERROR.getText());
//             ResponseEntity.setCode(StatusMessageEnum.INTERNAL_SERVER_ERROR.getStatusCode());
//             ResponseEntity.setData(null);
//             e.printStackTrace();
//         }
//         return ResponseEntity;
//     }

//     public static <T> ObjResponseEntity<T> build(String message, Integer code) {
//         ObjResponseEntity<T> ResponseEntity= build();

//         ResponseEntity.setTimestamp(getTimestamp());
//         ResponseEntity.setSuccess(true);
//         ResponseEntity.setMessage(message);
//         ResponseEntity.setCode(code);
//         ResponseEntity.setData(null);
//         return ResponseEntity;
//     }

//     public static <T> ObjResponseEntity<T> build(String message, Integer code, T body) {
//         ObjResponseEntity<T> ResponseEntity = build();
//         ResponseEntity.setTimestamp(getTimestamp());
//         ResponseEntity.setSuccess(true);
//         ResponseEntity.setMessage(message);
//         ResponseEntity.setCode(code);
//         ResponseEntity.setData(body);
//         return ResponseEntity;
//     }

//     public static <T> ObjResponseEntity<T> success() {
//         ObjResponseEntity<T> ResponseEntity = build();
//         ResponseEntity.setTimestamp(getTimestamp());
//         ResponseEntity.setSuccess(true);
//         ResponseEntity.setMessage(MessageEnum.SUCCESS.getText());
//         ResponseEntity.setCode(StatusMessageEnum.OK.getStatusCode());
//         ResponseEntity.setData(null);
//         return ResponseEntity;
//     }
    
//     public static <T> ObjResponseEntity<T> fail(String message, Integer code) {
//         ObjResponseEntity<T> ResponseEntity = build();
//         ResponseEntity.setTimestamp(getTimestamp());
//         ResponseEntity.setSuccess(false);
//         ResponseEntity.setMessage(message);
//         ResponseEntity.setCode(code);
//         ResponseEntity.setData(null);
//         return ResponseEntity;
//     }

//     public static <T> ObjResponseEntity<T> fail() {
//         ObjResponseEntity<T> ResponseEntity = build();
//         ResponseEntity.setTimestamp(getTimestamp());
//         ResponseEntity.setSuccess(false);
//         ResponseEntity.setMessage(MessageEnum.INTERNAL_SERVER_ERROR.getText());
//         ResponseEntity.setCode(StatusMessageEnum.INTERNAL_SERVER_ERROR.getStatusCode());
//         ResponseEntity.setData(null);
//         return ResponseEntity;
//     }

}

