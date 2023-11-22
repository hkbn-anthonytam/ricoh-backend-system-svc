package poc.msa.system.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Entity
@Data
@Table(name="estate")
public class Estate {
    
    @Id
    @Column(name="estate_id")
    private int estateId;

    @OneToMany
    @JoinColumn(name="estate_id")
    private List<EstateName> estateNames;

    @Column(name="completion_year")
    private String completionYear;

    @OneToOne
    @JoinColumn(name="location_id")
    private District location;

    @Column(name="site_area")
    private float siteArea;

    @Column(name="no_of_rental_blocks")
    private int noOfRentalBlocks;

    @Column(name="total_rental_flats")
    private int totalRentalFlats;

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
