import styles from "./styles";
import React, {useState} from "react";
import {useNavigate} from "react-router-dom";

let Login = (props) => {

    const [loginName, setLoginName] = useState("")
    const [passwordName, setPasswordName] = useState("")
    const [emailName, setEmailName] = useState("")
    const [isRegistration, setRegistration] = useState(false)

    let inputLoginName = React.createRef();
    let inputPasswordName = React.createRef();
    let inputEmailName = React.createRef();

    let navigate = useNavigate()

    let activateRegistration = () => {
        setRegistration(true)
    }

    let activateLogin = () => {
        setRegistration(false)
    }

    let onChangeLoginName = () => {
        setLoginName(inputLoginName.current.value);
    }

    let onChangePasswordName = () => {
        setPasswordName(inputPasswordName.current.value);
    }

    let onChangeEmailName = () => {
        setEmailName(inputEmailName.current.value);
    }

    let login = () => {
        let body = {
            username: loginName,
            password: passwordName,
        }
        fetch('/signin', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then(response => {
            debugger
            if (response.status === 400)
                alert("Something went wrong. Try again.")
            if (response .status === 200)
                return response.json()
            else return response
        })
        .then(data => {
            debugger
            if (data !== undefined && data.accessToken !== undefined) {
                localStorage.setItem("Authorization", data.accessToken)
                localStorage.setItem("username", data.username)
                localStorage.setItem("useremail", data.email)
                if (data.roles.includes("ROLE_ADMIN"))
                    props.setAdmin(true)
                else props.setAdmin(false)
                props.setLogin(true)
            }
        })
    }

    let registration = () => {
        let body = {
            username: loginName,
            password: passwordName,
            email: emailName,
            role: [ "ROLE_USER" ]
        }
        fetch('/signup', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(body)
        }).then(response => {
            if (response.status === 400)
                alert(response.statusText)
            if (response.status === 200) {
                alert("User registered successfully!")
                setRegistration(false)
            }
        })
    }

    return (
        <div style={styles.loginForm}>
            <button style={styles.linkButton} onClick={() => activateRegistration()}>Registration</button>
            <button style={styles.linkButton} onClick={() => activateLogin()}>Login</button>
            {isRegistration ? (
                <div>
                    <h2>Registration</h2>
                    <div>
                        <input style={styles.input} placeholder={"Login"} ref={inputLoginName} value={loginName} onChange={onChangeLoginName}/>
                    </div>
                    <div>
                        <input style={styles.input} placeholder={"Password"} ref={inputPasswordName} value={passwordName} onChange={onChangePasswordName}/>
                    </div>
                    <div>
                        <input style={styles.input} placeholder={"E-mail"} ref={inputEmailName} value={emailName} onChange={onChangeEmailName}/>
                    </div>
                    <button style={styles.button} onClick={() => registration()}>Send registration</button>
                </div>
            ) : (
                <div>
                    <h2>Login</h2>
                    <div>
                        <input style={styles.input} placeholder={"Login"} ref={inputLoginName} value={loginName} onChange={onChangeLoginName}/>
                    </div>
                    <div>
                        <input style={styles.input} placeholder={"Password"} ref={inputPasswordName} value={passwordName} onChange={onChangePasswordName}/>
                    </div>
                    <button style={styles.button} onClick={() => login()}>Send login</button>
                </div>
            )}
        </div>
    )
};

export default Login;