package fr.btn.sdbmrestapi.metier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Marque {
    private int id;
    private String name;
    private Pays pays;
    private Fabricant fabricant;
}
