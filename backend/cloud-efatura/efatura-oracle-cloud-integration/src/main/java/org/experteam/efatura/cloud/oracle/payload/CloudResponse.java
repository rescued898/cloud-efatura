package org.experteam.efatura.cloud.oracle.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CloudResponse {
    private boolean success;
    private String jobId;
    private String jobGroup;
    private String message;

    public CloudResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
