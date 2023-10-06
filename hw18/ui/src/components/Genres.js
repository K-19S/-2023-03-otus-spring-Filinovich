import React, {useState} from "react";
import styles from "./styles"
import {Header} from "./App"

let Genres = (props) => {

    const [genre, setGenre] = useState(null)
    const [genreName, setGenreName] = useState("")

    let inputGenreName = React.createRef();

    let changeGenre = (genre) => {
        setGenre(genre)
        setGenreName(genre.name)
    }

    let deleteGenre = (id) => {
        fetch('/genres?id=' + id, {
            method: "DELETE"
        }).then(() => props.performGenres())
    }

    let onChangeGenreName = () => {
        setGenreName(inputGenreName.current.value);
    }

    let createNewGenre = () => {
        let body;
        if (genre == null) {
            body = {
                name: genreName
            }
            fetch('/genres', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(() => {
                clearGenre()
                props.performGenres()
            })
        } else {
            body = {
                id: genre.id,
                name: genreName
            }
            fetch('/genres?id=' + genre.id, {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(() => {
                clearGenre()
                props.performGenres()
            })
        }
    }

    let clearGenre = () => {
        setGenre(null)
        setGenreName("")
    }

    return (
        <div>
            <React.Fragment>
                <Header title={'Genres'}/>

                <div style={styles.halfWindow}>
                    <input style={styles.input} placeholder={"Name"} ref={inputGenreName} value={genreName} onChange={onChangeGenreName}/>
                </div>
                <button style={styles.button} onClick={() => createNewGenre()}>Save</button>
                <button style={styles.button} onClick={() => clearGenre()}>Clear</button>

                <table style={styles.table}>
                    <thead>
                    <tr style={styles.tableItem}>
                        <th style={styles.tableItem}>ID</th>
                        <th style={styles.tableItem}>Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        props.genres !== null && Array.isArray(props.genres) &&
                        props.genres.map((genre, i) => (
                            <tr style={styles.tableItem} key={i}>
                                <td style={styles.tableItem}>{genre.id}</td>
                                <td style={styles.tableItem}>{genre.name}</td>
                                <td style={styles.tableItem}><button onClick={() => changeGenre(genre)}>Change</button></td>
                                <td style={styles.tableItem}><button onClick={() => deleteGenre(genre.id)}>Delete</button></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        </div>
    )
}

export default Genres;