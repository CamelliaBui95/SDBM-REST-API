package fr.btn.sdbmrestapi.dto;

import fr.btn.sdbmrestapi.metier.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleWrapper {
    private List<Article> data;
    private int offset;
    private int pageSize;
    private int total;
}
