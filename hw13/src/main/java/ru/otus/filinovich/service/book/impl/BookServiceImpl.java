package ru.otus.filinovich.service.book.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.filinovich.dao.book.BookRepository;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.service.book.BookService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final JdbcMutableAclService aclService;


    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void save(Book book) {
        Long id = book.getId();
        bookRepository.save(book);
        if (id == null) {
            addDefaultAccessToNewBook(book);
        }
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    private void addDefaultAccessToNewBook(Book book) {
        ObjectIdentityImpl objectIdentity = new ObjectIdentityImpl(book.getClass(), book.getId());
        MutableAcl acl = aclService.createAcl(objectIdentity);
        GrantedAuthoritySid sid = new GrantedAuthoritySid("ROLE_ADMIN");
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
        acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
        sid = new GrantedAuthoritySid("ROLE_USER");
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, sid, true);
    }
}
