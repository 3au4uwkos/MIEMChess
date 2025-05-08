import React, { useState } from "react";
import '../AuthPage.css';
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { useHttp } from '../hooks/useEndpoints';

const AuthPage = () => {

    const apiBaseUrl = useHttp();
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [errors, setErrors] = useState({ username: "", password: "" });

    const handleSubmit = async (event) => {
        event.preventDefault();

        setErrors({ username: "", password: "" }); // Очистка ошибок при отправке

        const user_info = {
            username: username,
            password: password
        };

        let hasError = false;

        if (!username) {
            setErrors((prevErrors) => ({ ...prevErrors, username: "Имя пользователя не может быть пустым." }));
            hasError = true;
        }

        if (!password) {
            setErrors((prevErrors) => ({ ...prevErrors, password: "Пароль не может быть пустым." }));
            hasError = true;
        }

        if (hasError) return; // Если есть ошибки, не отправляем запрос

        try {
            // Запрос к бэкенду через axios
            const response = await axios.post(`${apiBaseUrl}/api/login`, user_info);

            // Если авторизация успешна, например, можно перенаправить на главную страницу
            if (response.data.success) {
                navigate("/MainPage");
            } else {
                setErrors((prevErrors) => ({ ...prevErrors, password: "Неверный логин или пароль." }));
            }
        } catch (error) {
            console.error("Ошибка при авторизации:", error);
            alert("Произошла ошибка, попробуйте снова.");
        }
    };

    return (
        <div className="AuthPage">
            <div className="UpperAuthPage">
                <div className="logos">
                    <img src="/img/HSE_logo.png" alt="HSE" />
                    <img src="/img/MIEM_logo.png" alt="MIEM" />
                    <img src="/img/MIEMChess_logo.png" alt="MIEMChess" />
                </div>
            </div>

            <div className="contentBlocks">

                <div className="leftBlock">
                    <h1>
                        ЭТО <br />
                        НЕ <br />
                        ПРОСТО <br />
                        <span className="highlighted">ШАХМАТЫ</span>
                    </h1>
                    <p className="descText">
                        ЭТО <span className="highlighted">КУРСАЧ</span> ДВУХ ПЕРВОКУРСНИКОВ
                        МИЭМа НИУ ВШЭ
                    </p>
                </div>

                <div className="rightBlock">
                    <h2>
                        <span className="highlighted">ВОЙДИТЕ</span>, <br /> ЧТОБЫ ИГРАТЬ
                    </h2>

                    <form className="authForm" onSubmit={handleSubmit}>
                        <label>Имя пользователя:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(event) => setUsername(event.target.value)}
                            style={{ border: errors.username ? "1px solid red" : "1px solid #ccc" }}
                        />
                        {errors.username && <div className="error-message">{errors.username}</div>}

                        <label>Пароль:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            style={{ border: errors.password ? "1px solid red" : "1px solid #ccc" }}
                        />
                        {errors.password && <div className="error-message">{errors.password}</div>}

                        <button type="submit">ВОЙТИ</button>
                    </form>

                    <div className="registerBlock">
                        <p className="registerText">Еще нет аккаунта?</p>
                        <span className="registerLink" onClick={() => navigate("/Registration")}>Зарегистрироваться</span>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default AuthPage;
