package com.example.clickapp.entity.template;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class AbsUUIDEntity extends AbsMainEntity{
    //    @Id
//    @GeneratedValue(generator = "uuid2")
//    @Type(type = "org.hibernate.PostgresUUIDType")
//    @GenericGenerator(name = "uuid2" , strategy = "org.hibernate.id.UUIDGenerator")
//    private UUID id;
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AbsUUIDEntity that = (AbsUUIDEntity) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
