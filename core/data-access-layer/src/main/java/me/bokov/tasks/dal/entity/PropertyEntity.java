package me.bokov.tasks.dal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@EqualsAndHashCode (callSuper = true)
@Data
@Entity
@Table (name = "configuration_property")
public class PropertyEntity extends AbstractBaseEntity {

    @ManyToOne (fetch = FetchType.LAZY, optional = false)
    @JoinColumn (name = "category_id", nullable = false)
    private CategoryEntity category;

    @Column (name = "key", nullable = false)
    private String key;

    @Column (name = "value", nullable = false)
    private String value;

}
