import React from "react";
import {Header} from "./App";
import styles from "./styles";

let MyBooks = (props) => {

    React.useEffect(() => {
        performMyBooks()
    }, [])

    let performMyBooks = () => {
        fetch('/userBooks', { headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(books => {
                debugger
                props.setMyBooks(books)
            });
    }

    function downloadBlob(bytes1, name) {
        let blob = decodeURIComponent(atob(bytes1).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));
        let binaryData = [];
        binaryData.push(blob);
        const blobUrl = URL.createObjectURL(new Blob(binaryData, {type: "application/zip"}));
        const link = document.createElement("a");
        link.href = blobUrl;
        link.download = name;
        document.body.appendChild(link);
        link.dispatchEvent(
            new MouseEvent('click', {
                bubbles: true,
                cancelable: true,
                view: window
            })
        );
        document.body.removeChild(link);
    }

    let downloadBook = (book) => {
        fetch('/books/' + book.id + '/file', {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('Authorization')
            }
        }).then(response => {
            return response.json()
        }).then(file => {
                    downloadBlob(file.bytes, file.name)
                }
            );
    }

    let deleteBook = (id) => {
        fetch('/deleteMyBook?id=' + id, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('Authorization')
            }
        }).then(response => response.json())
            .then(books => props.setMyBooks(books));
    }

    let setRating = (bookId, rating) => {
        debugger
        fetch('/setRating?bookId=' + bookId + '&rating=' + rating, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + localStorage.getItem('Authorization')
            }
        }).then(response => response.json())
            .then(data => {
                debugger;
                props.setMyBooks(data)
            });
    }

    return (
        <div>
            <React.Fragment>
                <Header title={'My books'}/>
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
                        props.myBooks !== null && Array.isArray(props.myBooks) &&
                        props.myBooks.map((raw, i) => (
                            <tr style={styles.tableItem} key={i}>
                                <td style={styles.tableItem}>{raw.book.name}</td>
                                <td style={styles.tableItem}>{raw.book.genre.name}</td>
                                <td style={styles.tableItem}>{raw.book.authors.map(author => author.name).join(", ")}</td>
                                <td style={styles.tableItemInline}>
                                    { raw.rating != null ? (
                                        raw.rating.rating
                                    ) : (
                                        <div>
                                            <button onClick={() => setRating(raw.book.id, 5)}>5</button>
                                            <button onClick={() => setRating(raw.book.id, 4)}>4</button>
                                            <button onClick={() => setRating(raw.book.id, 3)}>3</button>
                                            <button onClick={() => setRating(raw.book.id, 2)}>2</button>
                                            <button onClick={() => setRating(raw.book.id, 1)}>1</button>
                                        </div>
                                    )}
                                </td>
                                <td style={styles.tableItem}><button onClick={() => downloadBook(raw.book)}>Download</button></td>
                                <td style={styles.tableItem}><button onClick={() => deleteBook(raw.book.id)}>Delete</button></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        </div>
    )
}

export default MyBooks