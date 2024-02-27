package fr.btn.sdbmrestapi.hateos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class HateOs {
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Link> links;
    public void addLink(Link link) {
        if(links == null)
            links = new ArrayList<>();
        links.add(link);
    }

}
