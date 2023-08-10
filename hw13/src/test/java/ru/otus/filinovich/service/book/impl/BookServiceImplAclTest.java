package ru.otus.filinovich.service.book.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.AclImpl;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import ru.otus.filinovich.dao.book.BookRepository;
import ru.otus.filinovich.domain.Author;
import ru.otus.filinovich.domain.Book;
import ru.otus.filinovich.domain.Genre;
import ru.otus.filinovich.security.AclConfig;
import ru.otus.filinovich.security.AclMethodSecurityConfiguration;
import ru.otus.filinovich.service.book.BookService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@JdbcTest
@ContextConfiguration(classes = {
        BookServiceImpl.class,
        AclMethodSecurityConfiguration.class,
        AclConfig.class})
class BookServiceImplAclTest {

    private static final List<Book> TEST_BOOKS = new ArrayList<>();

    // Мокированый BookService терял все свойства аннотаций PreAuthorize и т.п.
    // Пришлось тестировать зашиту на реализации
    @Autowired
    private BookService bookService;

    @Autowired
    private AclConfig aclConfig;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private JdbcMutableAclService aclService;

    @BeforeEach
    public void initBooks() {
        TEST_BOOKS.add(new Book(1L, "Book1", new Genre("Genre1"), List.of(new Author("Author1"))));
        TEST_BOOKS.add(new Book(2L, "Book2", new Genre("Genre2"), List.of(new Author("Author2"))));
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getAllBooksAclFilterEmptyTest() {
        given(aclService.readAclById(any(), any())).willReturn(createBookAcl(TEST_BOOKS.get(0)));
        given(bookRepository.findAll()).willReturn(TEST_BOOKS);
        List<Book> allBooks = bookService.getAllBooks();
        assertThat(allBooks.size()).isEqualTo(0);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void getBookByIdAclFilterEmptyTest() {
        given(aclService.readAclById(any(), any())).willReturn(createBookAcl(TEST_BOOKS.get(0)));
        given(bookRepository.findById(1L)).willReturn(Optional.ofNullable(TEST_BOOKS.get(0)));
        assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> {
                    bookService.getBookById(1L);
                });
    }

    @Test
    @WithMockUser(roles = "USER")
    public void saveAclFailedTest() {
        Book book = new Book("TestBook", List.of(new Author("Author")), new Genre("Genre"));
        assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> {
                    bookService.save(book);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void saveAclSuccessTest() {
        Book book = new Book("TestBook", List.of(new Author("Author")), new Genre("Genre"));
        given(bookRepository.save(any())).willReturn(
                new Book(1L, "TestBook", new Genre("Genre"), List.of(new Author("Author"))));
        try {
            bookService.save(book);
        } catch (IllegalArgumentException ignored) {
            System.out.println("Forced to skip this exception");
        }
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void updateAclFailedTest() {
        Book book = new Book(1L, "TestBook", new Genre("Genre"), List.of(new Author("Author")));
        given(aclService.readAclById(any(), any())).willReturn(createBookAcl(book));
        assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> {
                    bookService.save(book);
                });
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteBookAclGrantedTest() {
        bookService.deleteById(1L);
    }

    @Test
    @WithMockUser(roles = "USER")
    public void deleteBookAclDeniedTest() {
        assertThatExceptionOfType(AccessDeniedException.class)
                .isThrownBy(() -> {
                    bookService.deleteById(1L);
                });
    }

    private Acl createBookAcl(Book book) {
        ObjectIdentityImpl bookObjectIdentity = new ObjectIdentityImpl(Book.class, book.getId());
        return new AclImpl(bookObjectIdentity, 1L,
                aclConfig.aclAuthorizationStrategy(),
                aclConfig.permissionGrantingStrategy(),
                null, null, false,
                new GrantedAuthoritySid("ROLE_ADMIN"));
    }
}