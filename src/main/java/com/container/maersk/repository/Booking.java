package com.container.maersk.repository;

import java.time.Instant;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Table
@AllArgsConstructor
@Getter
public class Booking {

    @Id
    @PrimaryKeyColumn
    @Column("booking_ref")
    private UUID bookingRef;

    @Column("container_type")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String containerType;

    @Column("container_size")
    @CassandraType(type = CassandraType.Name.INT)
    private Integer containerSize;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String origin;

    @CassandraType(type = CassandraType.Name.TEXT)
    private String destination;

    @CassandraType(type = CassandraType.Name.INT)
    private Integer quantity;

    @Column("creation_time")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant creationTime;
}
