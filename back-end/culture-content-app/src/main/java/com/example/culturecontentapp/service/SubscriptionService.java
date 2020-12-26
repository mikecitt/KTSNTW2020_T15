package com.example.culturecontentapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.example.culturecontentapp.exception.AccountNotFoundException;
import com.example.culturecontentapp.exception.CulturalOfferNotFoundException;
import com.example.culturecontentapp.exception.UserAlreadySubscribedException;
import com.example.culturecontentapp.exception.UserNotSubscribedException;
import com.example.culturecontentapp.model.Account;
import com.example.culturecontentapp.model.CulturalOffer;
import com.example.culturecontentapp.model.User;
import com.example.culturecontentapp.payload.response.SubscriptionResponse;
import com.example.culturecontentapp.repository.AccountRepository;
import com.example.culturecontentapp.repository.CulturalOfferRepository;
import com.example.culturecontentapp.repository.NewsRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final AccountRepository accountRepository;
    private final CulturalOfferRepository culturalOfferRepository;
    private final NewsRepository newsRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SubscriptionService(AccountRepository accountRepository, CulturalOfferRepository culturalOfferRepository,
            NewsRepository newsRepository, ModelMapper modelMapper) {

        this.accountRepository = accountRepository;
        this.culturalOfferRepository = culturalOfferRepository;
        this.newsRepository = newsRepository;
        this.modelMapper = modelMapper;
    }

    public ResponseEntity<Void> add(Long id) {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Optional<Account> entityUser = accountRepository.findByEmail(loggedUser.getUsername());
        if (!entityUser.isPresent()) {
            throw new AccountNotFoundException("Provided account is not found in the database");
        }

        Optional<CulturalOffer> entityCulturalOffer = culturalOfferRepository.findById(id);
        if (!entityCulturalOffer.isPresent()) {
            throw new CulturalOfferNotFoundException("Provided id is not found in the database");
        }

        User user = (User) entityUser.get();
        CulturalOffer culturalOffer = (CulturalOffer) entityCulturalOffer.get();

        if (user.getSubscriptions().contains(culturalOffer))
            throw new UserAlreadySubscribedException("User already subscribed to cultural offer.");

        user.getSubscriptions().add(culturalOffer);
        accountRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Void> delete(Long id) {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Optional<Account> entityUser = accountRepository.findByEmail(loggedUser.getUsername());
        if (!entityUser.isPresent()) {
            throw new AccountNotFoundException("Provided account is not found in the database");
        }

        Optional<CulturalOffer> entityCulturalOffer = culturalOfferRepository.findById(id);
        if (!entityCulturalOffer.isPresent()) {
            throw new CulturalOfferNotFoundException("Provided id is not found in the database");
        }

        User user = (User) entityUser.get();
        CulturalOffer culturalOffer = (CulturalOffer) entityCulturalOffer.get();

        if (!user.getSubscriptions().contains(culturalOffer))
            throw new UserNotSubscribedException("User is not subscribed to cultural offer.");

        user.getSubscriptions().remove(culturalOffer);
        accountRepository.save(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Page<SubscriptionResponse>> get(Pageable pageable) {
        org.springframework.security.core.userdetails.User loggedUser = (org.springframework.security.core.userdetails.User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        Optional<Account> entity = accountRepository.findByEmail(loggedUser.getUsername());

        if (!entity.isPresent()) {
            throw new AccountNotFoundException("Provided account is not found in the database");
        }

        List<SubscriptionResponse> subscriptions = new ArrayList<SubscriptionResponse>();
        User user = (User) entity.get();
        user.getSubscriptions().forEach(culturalOffer -> culturalOffer.getNews().forEach(news -> {
            SubscriptionResponse resp = new SubscriptionResponse();
            resp = modelMapper.map(news, SubscriptionResponse.class);
            resp.setCulturalOfferName(culturalOffer.getName());
            subscriptions.add(resp);
        }));
        return new ResponseEntity<>(new PageImpl<>(subscriptions.stream().collect(Collectors.toList())), HttpStatus.OK);

    }
}