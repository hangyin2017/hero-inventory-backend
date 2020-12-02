package com.hero.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "suppliers_id")
    private Long id;

    @Column(name = "suppliers_name")
    private String name;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "website")
    private String website;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "created_time")
    private Date createdTime;

    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    @Column(name = "comments")
    private String comments;

    //@OneToMany(mappedBy = "supplier")
    //private Set<PurchaseOrder> purchaseOrders;

}
