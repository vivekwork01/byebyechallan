package com.byebyechallan.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import lombok.Getter;

@Entity
@Getter
@Table(name = "core_vehicle_type_m")
public class CoreVehicleTypeEntity {

    @Id
    @Column(name = "vehicle_type_id")
    private String vehicleTypeId;

    @Column(name = "vehicle_type_name")
    private String vehicleTypeName;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "created_by")
    private long createdBy;

    @Column(name = "created_time")
    private Timestamp createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_category_id", referencedColumnName = "vehicle_category_id", insertable = false,
            updatable = false)
    private CoreVehicleCategoryEntity vehicleCategoryEntity;
}
