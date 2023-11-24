package peaksoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.model.enums.AppStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "applications")
@Getter
@Setter
@NoArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    private String developer;
    private String version;
    @Enumerated(EnumType.STRING)
    private AppStatus appStatus;
    private String genreName;
    private LocalDate localDate;

    @ManyToMany(cascade = {CascadeType.MERGE
            , CascadeType.DETACH
            , CascadeType.PERSIST
            , CascadeType.REFRESH}, mappedBy = "applications")
    private List<User> users;
    @ManyToOne(cascade = {CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.PERSIST})
    @JoinColumn(name = "genreName_id")
    private Genre genre;
    @Transient
    private Long genreId;


}
