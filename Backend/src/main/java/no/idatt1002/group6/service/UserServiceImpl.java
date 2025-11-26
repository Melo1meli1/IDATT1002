package no.idatt1002.group6.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import no.idatt1002.group6.model.User;
import no.idatt1002.group6.repository.UserRepository;

import no.idatt1002.group6.exeptions.ResourceNotFoundException;

/**
 * Service class for handling User-related operations
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository uRepository;

    /**
     * Returns the authenticated user
     * 
     * @return the authenticated user
     */
    @Override
    public User getUser() {
        Authentication authorization = SecurityContextHolder.getContext().getAuthentication();
        String id = authorization.getName();
        return uRepository.findByEmail(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: "));
    }

}
