import React, {useState} from "react";
import styles from "./styles";
import {Header} from "./App"

let Authors = (props) => {

    const [author, setAuthor] = useState(null)
    const [authorName, setAuthorName] = useState("")

    let inputAuthorName = React.createRef();

    let changeAuthor = (author) => {
        setAuthor(author)
        setAuthorName(author.name)
    }

    let deleteAuthor = (id) => {
        fetch('/authors?' + new URLSearchParams({ id: id}), {
            method: "DELETE"
        }).then(() => props.performAuthors())
    }

    let onChangeAuthorName = () => {
        setAuthorName(inputAuthorName.current.value);
    }

    let createNewAuthor = () => {
        let body;
        if (author == null) {
            body = {
                name: authorName
            }
            fetch('/authors', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(() => {
                clearAuthor()
                props.performAuthors()
            })
        } else {
            body = {
                id: author.id,
                name: authorName
            }
            fetch('/authors', {
                method: 'PUT',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(body)
            }).then(() => {
                clearAuthor()
                props.performAuthors()
            })
        }
    }

    let clearAuthor = () => {
        setAuthor(null)
        setAuthorName("")
    }

    return (
        <div>
            <React.Fragment>
                <Header title={'Authors'}/>

                <div style={styles.halfWindow}>
                    <input style={styles.input} placeholder={"Name"} ref={inputAuthorName} value={authorName} onChange={onChangeAuthorName}/>
                </div>
                <button style={styles.button} onClick={() => createNewAuthor()}>Save</button>
                <button style={styles.button} onClick={() => clearAuthor()}>Clear</button>

                <table style={styles.table}>
                    <thead>
                    <tr style={styles.tableItem}>
                        <th style={styles.tableItem}>ID</th>
                        <th style={styles.tableItem}>Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        props.authors !== null && Array.isArray(props.authors) &&
                        props.authors.map((author, i) => (
                            <tr style={styles.tableItem} key={i}>
                                <td style={styles.tableItem}>{author.id}</td>
                                <td style={styles.tableItem}>{author.name}</td>
                                <td style={styles.tableItem}><button onClick={() => changeAuthor(author)}>Change</button></td>
                                <td style={styles.tableItem}><button onClick={() => deleteAuthor(author.id)}>Delete</button></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        </div>
    )
}

export default Authors;