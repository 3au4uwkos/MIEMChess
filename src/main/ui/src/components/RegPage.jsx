import React from "react";
import '../RegPage.css';
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import axios from "axios";
import { useHttp } from '../hooks/useEndpoints';

const RegPage = () => {
    const apiBaseUrl = useHttp();
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errors, setErrors] = useState({ username: "", password: "", confirmPassword: "" });

    const handleSubmit = async (event) => {
        event.preventDefault();
        setErrors({ username: "", password: "", confirmPassword: "" });

        const user_info = {
            username: username,
            password: password,
            confirmPassword: confirmPassword
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
        if (password !== confirmPassword) {
            setErrors((prevErrors) => ({ ...prevErrors, confirmPassword: "Пароли не совпадают." }));
            hasError = true;
        }

        if (hasError) return; // Если есть ошибки, не отправляем запрос

        try {
            // Запрос на сервер для регистрации
            const response = await axios.post(`${apiBaseUrl}/api/login`, user_info);

            // Если регистрация успешна, можно перенаправить на страницу авторизации
            if (response.data.success) {
                navigate("/MainPage"); // Перенаправляем на главную страницу
            } else {
                alert("Ошибка при регистрации! Попробуйте снова.");
            }
        } catch (error) {
            console.error("Ошибка при регистрации:", error);
            alert("Произошла ошибка, попробуйте снова.");
        }
    }
    return (
        <div className="RegPage">
            <div className="UpperRegPage">
                <div className="logos">
                    <img src="/img/HSE_logo.png" alt="HSE" />
                    <img src="/img/MIEM_logo.png" alt="MIEM" />
                    <img src="/img/MIEMChess_logo.png" alt="MIEMChess" />
                </div>
            </div>

            <div className="contentBlocksReg">

                <div className="leftBlockReg">
                    <h1>
                        ЭТО <br />
                        НЕ <br />
                        ПРОСТО <br />
                        <span className="highlighted">ШАХМАТЫ</span>
                    </h1>
                    <p className="descTextReg">
                        ЭТО <span className="highlighted">КУРСАЧ</span> ДВУХ ПЕРВОКУРСНИКОВ
                        МИЭМа НИУ ВШЭ
                    </p>
                </div>


                <div className="rightBlockReg">
                    <h2>
                        <span className="highlighted">ЗАПОЛНИТЕ</span> ДАННЫЕ<br /> ПОЛЬЗОВАТЕЛЯ
                    </h2>

                    <form className="regForm" onSubmit={handleSubmit}>
                        <label>Имя пользователя:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(event) => setUsername(event.target.value)}
                            style={{
                                border: errors.username ? "1px solid red" : "1px solid #ccc"
                            }}
                        />
                        {errors.username && (
                            <div className="error-message">{errors.username}</div>
                        )}

                        <label>Пароль:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(event) => setPassword(event.target.value)}
                            style={{
                                border: errors.password ? "1px solid red" : "1px solid #ccc"
                            }}
                        />
                        {errors.password && (
                            <div className="error-message">{errors.password}</div>
                        )}

                        <label>Повторите пароль:</label>
                        <input
                            type="password"
                            value={confirmPassword}
                            onChange={(event) => setConfirmPassword(event.target.value)}
                            style={{
                                border: errors.confirmPassword ? "1px solid red" : "1px solid #ccc"
                            }}
                        />
                        {errors.confirmPassword && (
                            <div className="error-message">{errors.confirmPassword}</div>
                        )}

                        <button type="submit">РЕГИСТРАЦИЯ</button>
                    </form>

                    <div className="authBlock">
                        <p className="authText">Уже есть аккаунт?</p>
                        <span className="authLink" onClick={() => navigate("/")}>Войти</span>
                    </div>
                </div>
            </div>
        </div>
    );
}
export default RegPage;
