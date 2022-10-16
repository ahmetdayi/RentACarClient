//package com.yunusAhmet.rentACar.core.validator;
//
//import com.yunusAhmet.rentACar.dto.CreateCustomerRequest;
//import com.yunusAhmet.rentACar.dto.UpdateCustomerRequest;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//
//public class UpdateCustomerPasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
//
//    @Override
//    public void initialize(PasswordMatches constraintAnnotation) {
//    }
//    @Override
//    public boolean isValid(Object obj, ConstraintValidatorContext context){
//        UpdateCustomerRequest customer = ( UpdateCustomerRequest) obj;
//        return customer.getPassword().equals(customer.getMatchingPassword());
//    }
//
//
//}
