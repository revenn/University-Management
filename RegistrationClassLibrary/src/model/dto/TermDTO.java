
package model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TermDTO  implements Serializable{
    private HallDTO hall;
    private String weekParity;
    private String dayOfTheWeek;
    private Integer[] timeTable;
}
