package fr.btn.sdbmrestapi.metier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pays {
    private int id;
    private String name;
    private Continent continent;
}
