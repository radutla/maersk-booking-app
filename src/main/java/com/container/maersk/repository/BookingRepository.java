package com.container.maersk.repository;

import java.math.BigInteger;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, BigInteger> {
}
