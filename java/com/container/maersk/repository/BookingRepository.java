package com.container.maersk.repository;

import java.math.BigInteger;
import org.springframework.data.cassandra.repository.ReactiveCassandraRepository;
import org.springframework.stereotype.Repository;
import com.container.maersk.model.Booking;

@Repository
public interface BookingRepository extends ReactiveCassandraRepository<Booking, BigInteger> {
}
