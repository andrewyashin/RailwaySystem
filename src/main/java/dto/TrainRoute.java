package dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainRoute {
    private Long trainId;
    private Long routeId;

    private Long compartmentFree;
    private Long deluxeFree;
    private Long berthFree;

    private String fromDate;
    private String toDate;

    private String fromCity;
    private String toCity;

    private Double compartmentPrice;
    private Double deluxePrice;
    private Double berthPrice;

    private Double distance;
}
