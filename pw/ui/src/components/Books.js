import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import {Header} from "./App";
import styles from "./styles";
import myBooks from "./MyBooks";

let Books = (props) => {
    const [books, setBooks] = useState(null)

    const navigate = useNavigate();

    React.useEffect(() => {
        debugger
        performBooks()
    }, [])

    let performBooks = () => {
        fetch('/books', { headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(books => {
                setBooks(books)
            });
    }

    let addBook = (book) => {
        fetch('/addUserbook?bookId=' + book.id, {
                method: 'POST',
                headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(books => {
                props.setMyBooks(books)
            });
    }

    let isBookIncluded = (book) => {
        debugger
        for (let i = 0; i < props.myBooks.length; i++) {
            if (props.myBooks[i].book.id === book.id) {
                return true
            }
        }
        return false
    }

    return (
        <div>
            <React.Fragment>
                <Header title={'Books'}/>
                <table style={styles.table}>
                    <thead>
                    <tr style={styles.tableItem}>
                        <th style={styles.tableItem}>Name</th>
                        <th style={styles.tableItem}>Genre</th>
                        <th style={styles.tableItem}>Authors</th>
                        <th style={styles.tableItem}>Rating</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        books !== null && Array.isArray(books) &&
                        books.map((book, i) => (
                            <tr style={styles.tableItem} key={i}>
                                <td style={styles.tableItem}>{book.name}</td>
                                <td style={styles.tableItem}>{book.genre.name}</td>
                                <td style={styles.tableItem}>{book.authors.map(author => author.name).join(", ")}</td>
                                <td style={styles.tableItem}>{book.rating}</td>
                                {props.myBooks != null && isBookIncluded(book) === true ? (
                                    <h4>In your library</h4>
                                ) : (
                                    <td style={styles.tableItem}><button onClick={() => addBook(book)}>Add book</button></td>
                                )}
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        </div>
    )
}

export default Books;