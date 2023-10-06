import {HashRouter, Link, Route, Routes} from "react-router-dom";
import Books from "./Books";
import React, {useState} from "react";
import Book from "./Book";
import Genres from "./Genres";
import styles from "./styles"
import Authors from "./Authors";

export const Header = (props) => (
    <h1>{props.title}</h1>
);

function App() {

    const [genres, setGenres] = useState(null)
    const [authors, setAuthors] = useState(null)
    const [selectedBook, setSelectedBook] = useState(null)
    const [comments, setComments] = useState(null)

    let performGenres = () => {
        fetch('/genres')
            .then(response => response.json())
            .then(genres => setGenres(genres));
    }

    let performAuthors = () => {
        fetch('/authors')
            .then(response => response.json())
            .then(authors => setAuthors(authors));
    }

    let performComments = (book) => {
        fetch('/comments?' + new URLSearchParams({ bookId: book.id}))
            .then(response => response.json())
            .then(comments => setComments(comments));
    }

    React.useEffect(() => {
        performGenres()
        performAuthors()
    }, [])

    return (
        <HashRouter>
            <nav>
                <Link style={styles.link} to="/">Books</Link>
                <Link style={styles.link} to="/book">New Book</Link>
                <Link style={styles.link} to="/genres">Genres</Link>
                <Link style={styles.link} to="/authors">Authors</Link>
            </nav>

            <Routes>
                <Route path='/' element={<Books selectedBook={selectedBook} setSelectedBook={setSelectedBook} setComments={setComments} performComments={performComments}/>}/>
                <Route path='/book' element={<Book book={selectedBook} setSelectedBook={setSelectedBook} genres={genres} authors={authors} comments={comments} performComments={performComments}/>}/>
                <Route path='/genres' element={<Genres genres={genres} setGenres={setGenres} performGenres={performGenres}/>}/>
                <Route path='/authors' element={<Authors authors={authors} setAuthors={setAuthors} performAuthors={performAuthors}/>}/>
            </Routes>
        </HashRouter>
    );
}

export default App;