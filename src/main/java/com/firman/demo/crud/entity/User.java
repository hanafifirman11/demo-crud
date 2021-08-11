package com.firman.demo.crud.entity;

import com.firman.demo.crud.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
@ToString(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@With
@SQLDelete(sql = "UPDATE users SET status = 'INACTIVE' WHERE id=?")
@Where(clause = "status='ACTIVE'")
public class User extends BaseEntity {

    @Id
    @SequenceGenerator(name="users_id_seq", sequenceName="users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator= "users_id_seq")
    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String address;
    private String phoneNumber;
    private BigDecimal salary;
    private String password;
    private String status;

}
