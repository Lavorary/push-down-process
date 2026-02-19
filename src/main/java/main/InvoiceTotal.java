package main;

import lombok.*;

import java.beans.ConstructorProperties;


@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString

public class InvoiceTotal {
    private int id;
    private String clientName;
    private StatusEnum status;
    private double total;





}
