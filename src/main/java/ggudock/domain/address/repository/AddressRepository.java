package ggudock.domain.address.repository;

import ggudock.domain.address.entity.Address;
import ggudock.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Long> {
    List<Address> findAddressByUser_Email(String userEmail);
    Optional<Address> findAddressByDefaultAddressAndUser_Email(boolean defaultAddress,String userEmail);
    boolean existsByAddressNumberAndUser_Email(String addressNumber, String userEmail);
}