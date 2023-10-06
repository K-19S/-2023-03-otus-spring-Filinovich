import React, {useEffect, useState} from 'react'
import {useNavigate} from "react-router-dom";
import styles from "./styles"
import {Header} from "./App"

let Books = (props) => {

    const [books, setBooks] = useState(null)

    const navigate = useNavigate();

    React.useEffect(() => {
        performBooks()
        props.setSelectedBook(null)
        props.setComments(null)
    }, [])

    let performBooks = () => {
        fetch('/books')
            .then(response => response.json())
            .then(books => setBooks(books));
    }

    let changeBook = (book) => {
        props.setSelectedBook(book)
        props.performComments(book)
        navigate("/book")
    }

    let deleteBook = (id) => {
        fetch('/books?id=' + id, {
            method: "DELETE"
        }).then(() => performBooks())
    }

    return (
        <div>
            <React.Fragment>
                <Header title={'Books'}/>
                <table style={styles.table}>
                    <thead>
                    <tr style={styles.tableItem}>
                        <th style={styles.tableItem}>ID</th>
                        <th style={styles.tableItem}>Name</th>
                        <th style={styles.tableItem}>Genre</th>
                        <th style={styles.tableItem}>Authors</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        books !== null && Array.isArray(books) &&
                        books.map((book, i) => (
                            <tr style={styles.tableItem} key={i}>
                                <td style={styles.tableItem}>{book.id}</td>
                                <td style={styles.tableItem}>{book.name}</td>
                                <td style={styles.tableItem}>{book.genre.name}</td>
                                <td style={styles.tableItem}>{book.authors.map(author => author.name).join(", ")}</td>
                                <td style={styles.tableItem}><button onClick={() => changeBook(book)}>Change</button></td>
                                <td style={styles.tableItem}><button onClick={() => deleteBook(book.id)}>Delete</button></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        </div>
    )
};

export default Books;