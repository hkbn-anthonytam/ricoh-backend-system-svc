package poc.msa.system.entity;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name="language")
public class Language {
    
    @Id
    @Column(name="language_code")
    @NotNull(message="validation.language.languagecode.notnull")
    private String languageCode;

    @Column(name="name")
    @Max(value=10, message="validation.language.name.max")
    private String name;

    @Column(name="created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate = Calendar.getInstance().getTime();

    @Column(name="created_by")
    private String createdBy;

    @Column(name="updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate = Calendar.getInstance().getTime();

    @Column(name="updated_by")
    private String updatedBy;
}
