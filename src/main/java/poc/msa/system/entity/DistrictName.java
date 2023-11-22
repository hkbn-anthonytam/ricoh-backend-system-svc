package poc.msa.system.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="district_name")
public class DistrictName {

    @Id
    @Column(name="district_name_id")
    private long districtNameId;

    @OneToOne
    @JoinColumn(name="language_code")
    private Language language;

    @Column(name="name")
    private String name;

    @Column(name="created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name="created_by")
    private String createdBy;

    @Column(name="updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name="updated_by")
    private String updatedBy;
    
}
