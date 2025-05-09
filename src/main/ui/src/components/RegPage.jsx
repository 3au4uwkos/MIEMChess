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
        if (username.length < 3 || username.length > 30) {
            setErrors((prevErrors) => ({ ...prevErrors, username: "Имя пользователя должно содержать от 3 до 30 символов" }));
            hasError = true;
        }
        if (!password) {
            setErrors((prevErrors) => ({ ...prevErrors, password: "Пароль не может быть пустым." }));
            hasError = true;
        }
        if (password.length < 8) {
            setErrors((prevErrors) => ({ ...prevErrors, password: "Пароль должен содержать хотя бы 8 символов" }));
            hasError = true;
        }
        if (password !== confirmPassword) {
            setErrors((prevErrors) => ({ ...prevErrors, confirmPassword: "Пароли не совпадают." }));
            hasError = true;
        }

        if (hasError) return;

        try {
            const response = await axios.post(`${apiBaseUrl}/api/login`, {
                username,
                password
            });

            // Проверяем успешный статус (200-299)
            if (response.status >= 200 && response.status < 300) {
                localStorage.setItem('authToken', response.data.accessToken);
                navigate("/MainPage");
            } else {
                setErrors({ password: "Неверный логин или пароль" });
            }
        } catch (error) {
            console.error("Ошибка авторизации:", error);

            if (error.response) {
                if (error.response.status === 401) {
                    setErrors({ password: "Неверный логин или пароль" });
                } else {
                    setErrors({ password: "Произошла неизвестная ошибка" });
                }
            } else if (error.request) {
                setErrors({ password: "Ошибка сети! Попробуйте снова!" });
            } else {
                setErrors({ password: "Ошибка при отправке запроса" });
            }
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
