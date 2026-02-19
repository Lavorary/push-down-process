package main;

import lombok.*;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class InvoiceStatusTotal {
    private int id;
    private String clientName;
    private StatusEnum status;
    private double total;

}
