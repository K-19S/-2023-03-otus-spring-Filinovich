import {HashRouter, Link, Route, Routes} from "react-router-dom";
import AdminBooks from "./AdminBooks";
import React, {useState} from "react";
import Book from "./Book";
import Genres from "./Genres";
import Login from "./Login";
import styles from "./styles"
import Authors from "./Authors";
import MyBooks from "./MyBooks";
import Books from "./Books";
import Users from "./Users";

export const Header = (props) => (
    <h1>{props.title}</h1>
);

function App() {

    const [genres, setGenres] = useState(null)
    const [authors, setAuthors] = useState(null)
    const [selectedBook, setSelectedBook] = useState(null)
    const [comments, setComments] = useState(null)
    const [myBooks, setMyBooks] = useState(null)
    const [login, setLogin] = useState()
    const [admin, setAdmin] = useState()

    let performGenres = () => {
        fetch('/genres', { headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(genres => setGenres(genres));
    }

    let performAuthors = () => {
        fetch('/authors', { headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(authors => setAuthors(authors));
    }

    let performComments = (book) => {
        fetch('/comments?' + new URLSearchParams({ bookId: book.id}), { headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(comments => setComments(comments));
    }

    let logout = () => {
        localStorage.removeItem('Authorization')
        localStorage.removeItem('username')
        localStorage.removeItem('useremail')
        setLogin(false)
    }

    React.useEffect(() => {
        performGenres()
        performAuthors()
    }, [])

    return (
        <div>
            {localStorage.getItem('Authorization') == null ? (
                <HashRouter>
                    <Routes>
                        <Route path='/*' element={<Login setLogin={setLogin} setAdmin={setAdmin}/>}/>
                    </Routes>
                </HashRouter>
            ) : ( admin ? (
                <HashRouter>
                    <nav>
                        <Link style={styles.link} to="/">Books</Link>
                        <Link style={styles.link} to="/book">New Book</Link>
                        <Link style={styles.link} to="/genres">Genres</Link>
                        <Link style={styles.link} to="/authors">Authors</Link>
                        <Link style={styles.link} to="/users">Users</Link>
                        <button onClick={() => logout()} style={styles.logoutLink}>Logout</button>
                    </nav>

                    <Routes>
                        <Route path='/' element={<AdminBooks selectedBook={selectedBook} setSelectedBook={setSelectedBook} setComments={setComments} performComments={performComments}/>}/>
                        <Route path='/book' element={<Book book={selectedBook} setSelectedBook={setSelectedBook} genres={genres} authors={authors} comments={comments} performComments={performComments}/>}/>
                        <Route path='/genres' element={<Genres genres={genres} setGenres={setGenres} performGenres={performGenres}/>}/>
                        <Route path='/authors' element={<Authors authors={authors} setAuthors={setAuthors} performAuthors={performAuthors}/>}/>
                        <Route path='/users' element={<Users/>}/>
                    </Routes>
                </HashRouter>
            ) : (
                <HashRouter>
                    <nav>
                        <Link style={styles.link} to="/library">Library</Link>
                        <Link style={styles.link} to="/">My books</Link>
                        <button onClick={() => logout()} style={styles.logoutLink}>Logout</button>
                    </nav>

                    <Routes>
                        <Route path='/' element={<MyBooks myBooks={myBooks} setMyBooks={setMyBooks}/>}/>
                        <Route path='/library' element={<Books myBooks={myBooks} setMyBooks={setMyBooks}/>}/>
                    </Routes>
                </HashRouter>
            ))}

        </div>
    );
}

export default App;