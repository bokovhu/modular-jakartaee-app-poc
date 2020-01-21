package me.bokov.tasks.dal.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode (callSuper = true)
@Entity
@Table (name = "configuration_category")
@Data
public class CategoryEntity extends AbstractBaseEntity {

    @Column (name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany (fetch = FetchType.LAZY, mappedBy = "category")
    private List <PropertyEntity> properties;

}
