package me.bokov.tasks.dal.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class AbstractBaseEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column (name = "id")
    private Long id;

}
