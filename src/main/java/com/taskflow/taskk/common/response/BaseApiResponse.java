package com.taskflow.taskk.common.response;


// import statements - 
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;    


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseApiResponse<T> {
    private boolean success; // true or false depending on the outcome of the API call
    private String message; // a message that can provide additional information about the API response, such as error details or success confirmation
    private T data; // the actual data being returned by the API, which can be of any type (e.g., a single object, a list of objects, etc.)
}
