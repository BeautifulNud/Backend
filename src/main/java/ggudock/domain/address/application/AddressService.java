package ggudock.domain.address.application;

import ggudock.domain.address.api.request.AddressRequest;
import ggudock.domain.address.application.response.AddressResponse;
import ggudock.domain.address.entity.Address;
import ggudock.domain.address.repository.AddressRepository;
import ggudock.domain.user.entity.User;
import ggudock.domain.user.repository.UserRepository;
import ggudock.global.exception.BusinessException;
import ggudock.global.exception.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressResponse saveAddress(AddressRequest addressRequest, String email) {
        User user = createUser(email);
        Address address = createAddress(addressRequest, user);
        if (isExistsByAddressNumber(address, email)) {
            throw new BusinessException(ErrorCode.DUPLICATED_ADDRESS);
        }
        Address saveAddress = save(address);
        return getDetail(saveAddress.getId());
    }

    private boolean isExistsByAddressNumber(Address address, String email) {
        return addressRepository.existsByAddressNumberAndUser_Email(address.getAddressNumber(), email);
    }

    private User createUser(String email) {
        return userRepository.findByEmail(email);
    }

    private Address save(Address address) {
        return addressRepository.save(address);
    }

    public void deleteAddress(Long addressId) {
        delete(addressId);
    }

    @Transactional(readOnly = true)
    public AddressResponse getDefaultAddress(String email) {
        Address address = checkDefaultAddress(email);
        return getDetail(address.getId());
    }

    @Transactional(readOnly = true)
    public Address checkDefaultAddress(String email) {
        return addressRepository.findAddressByDefaultAddressAndUser_Email(true, email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_DEFAULT_ADDRESS));
    }

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressList(String email) {
        List<Address> addressList = createAddressList(email);
        return createAddressResponseList(addressList);
    }

    @Transactional(readOnly = true)
    public Address getAddress(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ADDRESS));
    }

    @Transactional(readOnly = true)
    public AddressResponse getDetail(Long addressId) {
        Address address = getAddress(addressId);
        return createAddressResponse(address);
    }

    public AddressResponse changeAddress(AddressRequest addressRequest, Long addressId){
        Address address = getAddress(addressId);
        changeAddress(address,addressRequest);
        return getDetail(address.getId());
    }

    public AddressResponse changeDefault(String email,Long addressId){
        List<Address> addressList = createAddressList(email);
        findDefault(addressList);
        Address address = getAddress(addressId);
        address.changeDefault();
        return getDetail(address.getId());
    }

    private static void findDefault(List<Address> addressList) {
        for(Address address: addressList){
            if(address.isDefaultAddress()){
                address.changeDefault();
                break;
            }
        }
    }

    private static void changeAddress(Address address,AddressRequest addressRequest) {
        address.changeAddress(addressRequest.getName(),addressRequest.getAlias(),addressRequest.getPhoneNumber(),
                addressRequest.getAddressNumber(),addressRequest.getAddress(),addressRequest.getDetailAddress(),
                addressRequest.getExitCode(),addressRequest.getDeliveryMessage());
    }

    private static Address createAddress(AddressRequest addressRequest, User user) {
        return Address.builder()
                .name(addressRequest.getName())
                .alias(addressRequest.getAlias())
                .phoneNumber(addressRequest.getPhoneNumber())
                .addressNumber(addressRequest.getAddressNumber())
                .address(addressRequest.getAddress())
                .detailAddress(addressRequest.getDetailAddress())
                .exitCode(addressRequest.getExitCode())
                .deliveryMessage(addressRequest.getDeliveryMessage())
                .user(user)
                .build();
    }

    private static AddressResponse createAddressResponse(Address address) {
        return new AddressResponse(address);
    }

    private static List<AddressResponse> createAddressResponseList(List<Address> addressList) {
        return addressList.stream()
                .map(AddressResponse::new)
                .toList();
    }

    private List<Address> createAddressList(String email) {
        return addressRepository.findAddressByUser_Email(email);
    }

    private void delete(Long addressId) {
        addressRepository.delete(getAddress(addressId));
    }
}