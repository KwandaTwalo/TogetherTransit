package com.kwndtwalo.TogetherTransit.service.generic;

import com.kwndtwalo.TogetherTransit.domain.generic.Contact;
import com.kwndtwalo.TogetherTransit.service.IService;

import java.util.List;

public interface IContactService extends IService<Contact, Long> {
    List<Contact> getAllContacts();
}
