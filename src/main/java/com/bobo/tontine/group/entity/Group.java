package com.bobo.tontine.group.entity;

import com.bobo.tontine.profile.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Mamadou Bobo on 28/10/2022
 * @Project Tontine
 */

@Entity
@Table(name = "group_tbl")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date createdAt;
    private Long createBy;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "group_members",
            joinColumns = {
                    @JoinColumn(name = "group_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "member_id")
            }
    )
    private List<User> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group group = (Group) o;
        return id != null && Objects.equals(id, group.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
