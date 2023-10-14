package ru.otus.filinovich.resilince.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.filinovich.resilince.model.BookComment;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookCommentDto {

    private String id;

    private String text;

    private String bookId;

    public static BookCommentDto toDto(BookComment comment) {
        BookCommentDto dto = new BookCommentDto();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setBookId(comment.getBook().getId());
        return dto;
    }

    public static BookComment fromDto(BookCommentDto dto) {
        BookComment comment = new BookComment();
        comment.setId(dto.getId());
        comment.setText(dto.getText());
        return comment;
    }
}
