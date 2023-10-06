import React, {useState} from "react";
import Select from "react-select"
import {useNavigate} from "react-router-dom";
import styles from "./styles"
import {Header} from "./App"


let Book = (props) => {

    let inputBookName = React.createRef();
    let inputNewComment = React.createRef();

    const [bookName, setBookName] = useState("")
    const [bookGenre, setBookGenre] = useState("")
    const [bookAuthors, setBookAuthors] = useState("")
    const [newComment, setNewComment] = useState("")

    const navigate = useNavigate();

    React.useEffect(() => {
        debugger
        if (props.book !== null) {
            setBookName(props.book.name)
            setBookGenre(props.book.genre)
            setBookAuthors(props.book.authors)
        }
    }, [])

    let getGenres = () => {
        let data = []
        if (props.genres != null) {
            props.genres.forEach(function (genre) {
                data.push({
                    label: genre.name,
                    value: genre.id
                })
            })
        }
        return data
    }

    let getAuthors = () => {
        let data = []
        if (props.authors != null) {
            props.authors.forEach(function (author) {
                data.push({
                    label: author.name,
                    value: author.id
                })
            })
        }
        return data
    }

    let createNewBook = () => {
        debugger
        let authors = []

        if (bookAuthors != null) {
            for (let i in bookAuthors) {
                let author = bookAuthors[i]
                authors.push({
                    id: author.id,
                    name: author.name
                })
            }
        }

        let body;
        if (props.book == null) {
            body = {
                name: bookName,
                genre: {
                    id: bookGenre.id,
                    name: bookGenre.name
                },
                authors: authors
            }
            fetch('/books', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(onCancel)
        } else {
            body = {
                id: props.book.id,
                name: bookName,
                genre: {
                    id: bookGenre.id,
                    name: bookGenre.name
                },
                authors: authors
            }
            fetch('/books', {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(onCancel)
        }
    }

    let onChangeBookName = () => {
        setBookName(inputBookName.current.value);
    }

    let onChangeBookGenre = (value) => {
        setBookGenre({
            id: value.value,
            name: value.label
        });
    }

    let onChangeBookAuthors = (value) => {
        let authors = []
        if (value != null) {
            for (let i in value) {
                let author = value[i]
                authors.push({
                    id: author.value,
                    name: author.label
                })
            }
        }
        setBookAuthors(authors);
    }

    let onChangeNewComment = () => {
        setNewComment(inputNewComment.current.value);
    }

    let getSelectedAuthors = () => {
        let authors = []

        if (props.book != null) {
            for (let i in props.book.authors) {
                let author = props.book.authors[i]
                authors.push({
                    value: author.id,
                    label: author.name
                })
            }
        }
        debugger
        return authors
    }

    let onCancel = () => {
        props.setSelectedBook(null)
        setBookName(null)
        setBookGenre(null)
        setBookAuthors(null)
        navigate("/")
    }

    let createNewComment = () => {
        let body = {
                text: newComment,
                bookId: props.book.id
            }
            fetch('/comments', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(() => {
                clearComment()
                props.performComments(props.book)
            })
    }

    let clearComment = () => {
        setNewComment("")
    }

    return <div>
        <React.Fragment>
            <Header title={'Book'}/>
            <div style={styles.halfWindow}>
                <div>
                    <input style={styles.input} placeholder={"Name"} ref={inputBookName} value={bookName} onChange={onChangeBookName}/>
                </div>
                <Select defaultValue={props.book != null && {
                    label: props.book.genre.name,
                    value: props.book.genre.id
                }} placeholder={"Genre"} onChange={(value) => onChangeBookGenre(value)} options={getGenres()}/>
                <Select defaultValue={props.authors != null && getSelectedAuthors()
                } isMulti placeholder={"Authors"} onChange={(value) => onChangeBookAuthors(value)} options={getAuthors()}/>
                <button style={styles.button} onClick={() => createNewBook()}>Save</button>
                <button style={styles.button} onClick={onCancel}>Cancel</button>
            </div>
            <h3>Comment on this book:</h3>
            <div style={styles.halfWindow}>
                <input style={styles.input} placeholder={"Your comment"} ref={inputNewComment} value={newComment} onChange={onChangeNewComment}/>
            </div>
            <button style={styles.button} onClick={() => createNewComment()}>Save comment</button>
            <button style={styles.button} onClick={() => clearComment()}>Clear comment</button>
            <h3>Comments:</h3>
            <ul>
            {
                props.comments !== null && Array.isArray(props.comments) &&
                props.comments.map((comment, i) => (
                    <li>{comment.text}</li>
                ))
            }
            </ul>
        </React.Fragment>
    </div>
}

export default Book;