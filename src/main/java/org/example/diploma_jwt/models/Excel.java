package org.example.diploma_jwt.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "t_excel")
public class Excel {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String article = "";
    private String price = "";
    private String code = "";
    private String company = "";
    private String title = "";

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settings_id", nullable = false)
    private Settings settings;

    public Excel(Settings settings) {
        this.settings = settings;
    }

    public String toLog() {
        return "Excel{" +
                "article='" + article + '\'' +
                ", price='" + price + '\'' +
                ", code='" + code + '\'' +
                ", company='" + company + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
