import React, {useState} from "react";
import {Header} from "./App";
import styles from "./styles";

let Users = () => {

    const [users, setUsers] = useState(null)

    React.useEffect(() => {
        performUsers()
    }, [])

    let performUsers = () => {
        fetch('/users', { headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})})
            .then(response => response.json())
            .then(users => {
                setUsers(users)
            });
    }

    let banUser = (id) => {
        fetch('/users/ban?id=' + id, {
            method: "POST",
            headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})
        }).then(() => performUsers())
    }

    let unbanUser = (id) => {
        fetch('/users/unban?id=' + id, {
            method: "POST",
            headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})
        }).then(() => performUsers())
    }

    let deleteUser = (id) => {
        fetch('/users?id=' + id, {
            method: "DELETE",
            headers: new Headers({'Authorization': 'Bearer ' + localStorage.getItem('Authorization')})
        }).then(() => performUsers())
    }

    return (
        <div>
            <React.Fragment>
                <Header title={'Users'}/>
                <table style={styles.table}>
                    <thead>
                    <tr style={styles.tableItem}>
                        <th style={styles.tableItem}>ID</th>
                        <th style={styles.tableItem}>Username</th>
                        <th style={styles.tableItem}>Email</th>
                    </tr>
                    </thead>
                    <tbody>
                    {
                        users !== null && Array.isArray(users) &&
                        users.map((user, i) => (
                            <tr style={styles.tableItem} key={i}>
                                <td style={styles.tableItem}>{user.id}</td>
                                <td style={styles.tableItem}>{user.username}</td>
                                <td style={styles.tableItem}>{user.email}</td>
                                {user.banned ? (
                                    <td style={styles.tableItem}><button onClick={() => unbanUser(user.id)}>Unbanned</button></td>
                                ) : (
                                    <td style={styles.tableItem}><button onClick={() => banUser(user.id)}>Banned</button></td>
                                )}
                                <td style={styles.tableItem}><button onClick={() => deleteUser(user.id)}>Delete</button></td>
                            </tr>
                        ))
                    }
                    </tbody>
                </table>
            </React.Fragment>
        </div>
    )
}

export default Users