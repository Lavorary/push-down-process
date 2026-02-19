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
    private StatusEnum status;
    private double total;

}
