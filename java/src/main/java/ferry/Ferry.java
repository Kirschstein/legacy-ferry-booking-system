package ferry;

import lombok.Data;

@Data
public class Ferry {

    private int id;
    private String name;
    private int passengers;
    private int vehicles;
    private double weight;
    private int homePortId;

    public Ferry() {
        this.homePortId = 1;
    }
}
