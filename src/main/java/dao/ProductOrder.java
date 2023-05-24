
package dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrder {
    private int id;
    private LocalDate orderDate;
    private LocalDate deliveryDate;
    private String status;
    private int customerId;
}
