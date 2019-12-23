package eu.openreq.remote.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailData {
    private String toAddress;
    private String subject;
    private String htmlMessage;
    private String textMessage;
}
